package br.com.thallysprojetos.desafio2.dtos;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutRequestDTO {

    @Valid
    private List<CheckoutItemDTO> items;

}