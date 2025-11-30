package br.com.thallysprojetos.desafio2.services.impl;

import br.com.thallysprojetos.desafio2.dtos.CheckoutItemDTO;
import br.com.thallysprojetos.desafio2.dtos.CheckoutRequestDTO;
import br.com.thallysprojetos.desafio2.dtos.OrdersDTO;
import br.com.thallysprojetos.desafio2.events.OrderCreatedEvent;
import br.com.thallysprojetos.desafio2.exceptions.orders.OrdersNotFoundException;
import br.com.thallysprojetos.desafio2.exceptions.products.InsufficientStockException;
import br.com.thallysprojetos.desafio2.exceptions.products.ProductsNotFoundException;
import br.com.thallysprojetos.desafio2.mappers.OrdersMappers;
import br.com.thallysprojetos.desafio2.messaging.OrderEventPublisher;
import br.com.thallysprojetos.desafio2.models.OrderItem;
import br.com.thallysprojetos.desafio2.models.Orders;
import br.com.thallysprojetos.desafio2.models.Products;
import br.com.thallysprojetos.desafio2.repositories.OrdersRepository;
import br.com.thallysprojetos.desafio2.repositories.ProductsRepository;
import br.com.thallysprojetos.desafio2.services.OrdersService;
import br.com.thallysprojetos.desafio2.services.ProductsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OrdersServiceImpl implements OrdersService {

    private final OrdersRepository ordersRepository;
    private final ProductsRepository productsRepository;
    private final ProductsService productsService;
    private final OrdersMappers ordersMappers;
    private final OrderEventPublisher orderEventPublisher;

    @Transactional
    public OrdersDTO checkout(CheckoutRequestDTO checkoutRequest) {
        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (CheckoutItemDTO item : checkoutRequest.getItems()) {
            Products product = productsRepository.findById(item.getProductId())
                    .orElseThrow(() -> new ProductsNotFoundException("Product not found with Id: " + item.getProductId()));

            if (product.getStock() < item.getQuantity()) {
                throw new InsufficientStockException(
                        String.format("Insufficient stock for product ‘%s’. Available: %d, Requested: %d",
                                product.getName(), product.getStock(), item.getQuantity())
                );
            }

            BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            total = total.add(subtotal);

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(product.getPrice());

            orderItems.add(orderItem);
        }

        Orders order = new Orders();
        order.setTotal(total);
        order.setItems(orderItems);
        order.setCreatedAt(java.time.LocalDateTime.now());

        for (OrderItem item : orderItems) {
            item.setOrder(order);
        }

        Orders savedOrder = ordersRepository.save(order);

        for (CheckoutItemDTO item : checkoutRequest.getItems()) {
            productsService.decreaseStock(item.getProductId(), item.getQuantity());
        }

        OrderCreatedEvent event = new OrderCreatedEvent(
                savedOrder.getId(),
                savedOrder.getTotal(),
                savedOrder.getItems().size(),
                savedOrder.getCreatedAt()
        );
        orderEventPublisher.publishOrderCreated(event);

        return ordersMappers.toDTO(savedOrder);
    }

    public List<OrdersDTO> findAll() {
        List<Orders> orders = ordersRepository.findAll();
        return ordersMappers.toListDTO(orders);
    }

    public OrdersDTO findById(Long id) {
        Orders order = ordersRepository.findById(id)
                .orElseThrow(() -> new OrdersNotFoundException("Order not found with Id: " + id));
        return ordersMappers.toDTO(order);
    }

}