package br.com.thallysprojetos.desafio2.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDTO {

    private Long id;
    private List<OrdersItemDTO> items;
    private BigDecimal total;
    private LocalDateTime createdAt;

}