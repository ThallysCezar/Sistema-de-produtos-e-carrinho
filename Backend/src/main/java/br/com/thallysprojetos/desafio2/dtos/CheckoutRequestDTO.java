package br.com.thallysprojetos.desafio2.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutRequestDTO {

    @NotEmpty(message = "Carrinho não pode estar vazio")
    @Valid
    private List<CheckoutItemDTO> items;

}
