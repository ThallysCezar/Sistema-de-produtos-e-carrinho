package br.com.thallysprojetos.desafio2.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.com.thallysprojetos.desafio2.dtos.ProductsDTO;
import br.com.thallysprojetos.desafio2.models.Products;

import java.util.List;

@Component
public class OrderItemMappers {
    private final ModelMapper modelMapper;

    public OrderItemMappers(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProductsDTO toDTO(Products entity) {
        return modelMapper.map(entity, ProductsDTO.class);
    }

    public Products toEntity(ProductsDTO dto) {
        return modelMapper.map(dto, Products.class);
    }

    public List<ProductsDTO> toListDTO(List<Products> productsList) {
        return productsList.stream()
                .map(this::toDTO).toList();
    }

    public List<Products> toList(List<ProductsDTO> dtosList) {
        return dtosList.stream()
                .map(this::toEntity).toList();
    }

}