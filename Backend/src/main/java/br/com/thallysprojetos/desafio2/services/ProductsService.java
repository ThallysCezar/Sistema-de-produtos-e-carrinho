package br.com.thallysprojetos.desafio2.services;

import br.com.thallysprojetos.desafio2.dtos.ProductsDTO;

import java.util.List;

public interface ProductsService {

    List<ProductsDTO> findAll();

    ProductsDTO findById(Long id);

    ProductsDTO save(ProductsDTO productsDTO);

    ProductsDTO update(Long id, ProductsDTO productsDTO);

    void delete(Long id);

    void decreaseStock(Long productId, Integer quantity);

}