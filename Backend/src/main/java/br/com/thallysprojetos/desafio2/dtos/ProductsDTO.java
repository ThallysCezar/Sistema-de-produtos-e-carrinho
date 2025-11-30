package br.com.thallysprojetos.desafio2.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductsDTO {

    private Long id;

    @NotBlank(message = "Nome do produto é obrigatório")
    private String name;

    @NotNull(message = "Preço é obrigatório")
    @PositiveOrZero(message = "Preço não pode ser negativo")
    private BigDecimal price;

    @NotNull(message = "Estoque é obrigatório")
    @PositiveOrZero(message = "Estoque não pode ser negativo")
    private Integer stock;

}