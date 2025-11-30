package br.com.thallysprojetos.desafio2.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.thallysprojetos.desafio2.dtos.CheckoutItemDTO;
import br.com.thallysprojetos.desafio2.dtos.CheckoutRequestDTO;
import br.com.thallysprojetos.desafio2.dtos.OrdersDTO;
import br.com.thallysprojetos.desafio2.dtos.OrdersItemDTO;
import br.com.thallysprojetos.desafio2.exceptions.model.InsufficientStockException;
import br.com.thallysprojetos.desafio2.exceptions.model.ModelNotFoundException;
import br.com.thallysprojetos.desafio2.mappers.OrdersMappers;
import br.com.thallysprojetos.desafio2.models.OrderItem;
import br.com.thallysprojetos.desafio2.models.Orders;
import br.com.thallysprojetos.desafio2.models.Products;
import br.com.thallysprojetos.desafio2.repositories.OrdersRepository;
import br.com.thallysprojetos.desafio2.repositories.ProductsRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final ProductsRepository productsRepository;
    private final ProductsService productsService;
    private final OrdersMappers ordersMappers;

    @Transactional
    public OrdersDTO checkout(CheckoutRequestDTO checkoutRequest) {
        // Validar itens e estoque
        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (CheckoutItemDTO item : checkoutRequest.getItems()) {
            Products product = productsRepository.findById(item.getProductId())
                    .orElseThrow(() -> new ModelNotFoundException("Produto não encontrado com ID: " + item.getProductId()));

            // Verificar estoque
            if (product.getStock() < item.getQuantity()) {
                throw new InsufficientStockException(
                        String.format("Estoque insuficiente para o produto '%s'. Disponível: %d, Solicitado: %d",
                                product.getName(), product.getStock(), item.getQuantity())
                );
            }

            // Calcular subtotal
            BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            total = total.add(subtotal);

            // Criar item do pedido
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(product.getPrice());
            
            orderItems.add(orderItem);
        }

        // Criar pedido
        Orders order = new Orders();
        order.setTotal(total);
        order.setItems(orderItems);

        // Associar ordem aos itens
        for (OrderItem item : orderItems) {
            item.setOrder(order);
        }

        // Salvar pedido
        Orders savedOrder = ordersRepository.save(order);

        // Diminuir estoque dos produtos
        for (CheckoutItemDTO item : checkoutRequest.getItems()) {
            productsService.decreaseStock(item.getProductId(), item.getQuantity());
        }

        return ordersMappers.toDTO(savedOrder);
    }

    public List<OrdersDTO> findAll() {
        List<Orders> orders = ordersRepository.findAll();
        return ordersMappers.toListDTO(orders);
    }

    public OrdersDTO findById(Long id) {
        Orders order = ordersRepository.findById(id)
                .orElseThrow(() -> new ModelNotFoundException("Pedido não encontrado com ID: " + id));
        return ordersMappers.toDTO(order);
    }

}