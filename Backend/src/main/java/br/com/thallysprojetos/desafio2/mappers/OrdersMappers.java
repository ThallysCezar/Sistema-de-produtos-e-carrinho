package br.com.thallysprojetos.desafio2.mappers;

import org.springframework.stereotype.Component;

import br.com.thallysprojetos.desafio2.dtos.OrdersDTO;
import br.com.thallysprojetos.desafio2.dtos.OrdersItemDTO;
import br.com.thallysprojetos.desafio2.models.OrderItem;
import br.com.thallysprojetos.desafio2.models.Orders;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrdersMappers {

    public OrdersDTO toDTO(Orders entity) {
        if (entity == null) {
            return null;
        }
        
        OrdersDTO dto = new OrdersDTO();
        dto.setId(entity.getId());
        dto.setTotal(entity.getTotal());
        dto.setCreatedAt(entity.getCreatedAt());
        
        if (entity.getItems() != null) {
            List<OrdersItemDTO> itemsDTO = entity.getItems().stream()
                    .map(this::toItemDTO)
                    .collect(Collectors.toList());
            dto.setItems(itemsDTO);
        }
        
        return dto;
    }

    public OrdersItemDTO toItemDTO(OrderItem entity) {
        if (entity == null) {
            return null;
        }
        
        OrdersItemDTO dto = new OrdersItemDTO();
        dto.setId(entity.getId());
        dto.setProductId(entity.getProduct().getId());
        dto.setProductName(entity.getProduct().getName());
        dto.setQuantity(entity.getQuantity());
        dto.setPrice(entity.getPrice());
        
        return dto;
    }

    public List<OrdersDTO> toListDTO(List<Orders> ordersList) {
        return ordersList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

}