package br.com.thallysprojetos.desafio2.factories;

import br.com.thallysprojetos.desafio2.dtos.OrdersDTO;
import br.com.thallysprojetos.desafio2.dtos.OrdersItemDTO;
import br.com.thallysprojetos.desafio2.models.OrderItem;
import br.com.thallysprojetos.desafio2.models.Orders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrdersFactory {

    public static Orders createOrder() {
        return createOrder(1L, "3800.00");
    }

    public static Orders createOrder(Long id, String total) {
        Orders order = new Orders();
        order.setId(id);
        order.setTotal(new BigDecimal(total));
        order.setCreatedAt(LocalDateTime.now());
        order.setItems(new ArrayList<>());
        return order;
    }

    public static Orders createOrderWithItems() {
        Orders order = createOrder(1L, "3800.00");
        
        OrderItem item1 = new OrderItem();
        item1.setId(1L);
        item1.setOrder(order);
        item1.setProduct(ProductsFactory.createNotebook());
        item1.setQuantity(1);
        item1.setPrice(new BigDecimal("3500.00"));

        OrderItem item2 = new OrderItem();
        item2.setId(2L);
        item2.setOrder(order);
        item2.setProduct(ProductsFactory.createMouse());
        item2.setQuantity(2);
        item2.setPrice(new BigDecimal("150.00"));

        order.getItems().add(item1);
        order.getItems().add(item2);
        
        return order;
    }

    public static OrdersDTO createOrderDTO() {
        return createOrderDTO(1L, "3800.00");
    }

    public static OrdersDTO createOrderDTO(Long id, String total) {
        OrdersDTO orderDTO = new OrdersDTO();
        orderDTO.setId(id);
        orderDTO.setTotal(new BigDecimal(total));
        orderDTO.setCreatedAt(LocalDateTime.now());
        orderDTO.setItems(new ArrayList<>());
        return orderDTO;
    }

    public static OrdersDTO createOrderDTOWithItems() {
        OrdersDTO orderDTO = createOrderDTO(1L, "3800.00");
        
        OrdersItemDTO item1 = new OrdersItemDTO();
        item1.setId(1L);
        item1.setProductId(1L);
        item1.setProductName("Notebook Dell");
        item1.setQuantity(1);
        item1.setPrice(new BigDecimal("3500.00"));

        OrdersItemDTO item2 = new OrdersItemDTO();
        item2.setId(2L);
        item2.setProductId(2L);
        item2.setProductName("Mouse Gamer");
        item2.setQuantity(2);
        item2.setPrice(new BigDecimal("150.00"));

        orderDTO.getItems().add(item1);
        orderDTO.getItems().add(item2);
        
        return orderDTO;
    }

    public static List<Orders> createOrdersList() {
        List<Orders> orders = new ArrayList<>();
        orders.add(createOrder(1L, "3800.00"));
        orders.add(createOrder(2L, "500.00"));
        return orders;
    }

    public static List<OrdersDTO> createOrdersDTOList() {
        List<OrdersDTO> ordersDTO = new ArrayList<>();
        ordersDTO.add(createOrderDTO(1L, "3800.00"));
        ordersDTO.add(createOrderDTO(2L, "500.00"));
        return ordersDTO;
    }

}
