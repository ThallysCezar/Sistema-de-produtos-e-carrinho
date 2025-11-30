package br.com.thallysprojetos.desafio2.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.thallysprojetos.desafio2.dtos.ProductsDTO;
import br.com.thallysprojetos.desafio2.exceptions.model.ModelNotFoundException;
import br.com.thallysprojetos.desafio2.mappers.ProductsMappers;
import br.com.thallysprojetos.desafio2.models.Products;
import br.com.thallysprojetos.desafio2.repositories.ProductsRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductsService {

    private final ProductsRepository productsRepository;
    private final ProductsMappers modelMapper;

    public List<ProductsDTO> findAll() {
        List<Products> products = productsRepository.findAll();
        return modelMapper.toListDTO(products);
    }

    public ProductsDTO findById(Long id) {
        Products product = productsRepository.findById(id)
                .orElseThrow(() -> new ModelNotFoundException("Produto não encontrado com ID: " + id));
        return modelMapper.toDTO(product);
    }

    @Transactional
    public ProductsDTO save(ProductsDTO productsDTO) {
        validatePrice(productsDTO.getPrice());
        Products product = modelMapper.toEntity(productsDTO);
        Products saved = productsRepository.save(product);
        return modelMapper.toDTO(saved);
    }

    @Transactional
    public ProductsDTO update(ProductsDTO productsDTO) {
        Products existingProduct = productsRepository.findById(productsDTO.getId())
                .orElseThrow(() -> new ModelNotFoundException("Produto não encontrado com ID: " + productsDTO.getId()));
        
        validatePrice(productsDTO.getPrice());
        
        existingProduct.setName(productsDTO.getName());
        existingProduct.setPrice(productsDTO.getPrice());
        existingProduct.setStock(productsDTO.getStock());
        
        Products updated = productsRepository.save(existingProduct);
        return modelMapper.toDTO(updated);
    }

    @Transactional
    public void delete(Long id) {
        if (!productsRepository.existsById(id)) {
            throw new ModelNotFoundException("Produto não encontrado com ID: " + id);
        }
        productsRepository.deleteById(id);
    }

    @Transactional
    public void decreaseStock(Long productId, Integer quantity) {
        Products product = productsRepository.findById(productId)
                .orElseThrow(() -> new ModelNotFoundException("Produto não encontrado com ID: " + productId));
        
        if (product.getStock() < quantity) {
            throw new br.com.thallysprojetos.desafio2.exceptions.model.InsufficientStockException(
                    String.format("Estoque insuficiente para o produto '%s'. Disponível: %d, Solicitado: %d",
                            product.getName(), product.getStock(), quantity)
            );
        }
        
        product.setStock(product.getStock() - quantity);
        productsRepository.save(product);
    }

    private void validatePrice(BigDecimal price) {
        if (price != null && price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Preço não pode ser negativo");
        }
    }

}