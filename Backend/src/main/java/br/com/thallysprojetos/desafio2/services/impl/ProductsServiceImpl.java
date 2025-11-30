package br.com.thallysprojetos.desafio2.services.impl;

import br.com.thallysprojetos.desafio2.dtos.ProductsDTO;
import br.com.thallysprojetos.desafio2.exceptions.products.ProductsException;
import br.com.thallysprojetos.desafio2.exceptions.products.ProductsNotFoundException;
import br.com.thallysprojetos.desafio2.mappers.ProductsMappers;
import br.com.thallysprojetos.desafio2.models.Products;
import br.com.thallysprojetos.desafio2.repositories.ProductsRepository;
import br.com.thallysprojetos.desafio2.services.ProductsService;
import br.com.thallysprojetos.desafio2.utils.ValidationsProducts;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ProductsServiceImpl implements ProductsService {

    private final ProductsRepository productsRepository;
    private final ProductsMappers mappers;
    private final ValidationsProducts validationsProducts;

    public List<ProductsDTO> findAll() {
        List<Products> products = productsRepository.findAll();
        return mappers.toListDTO(products);
    }

    public ProductsDTO findById(Long id) {
        Products product = productsRepository.findById(id)
                .orElseThrow(() -> new ProductsNotFoundException("Product not found with Id: " + id));
        return mappers.toDTO(product);
    }

    @Transactional
    public ProductsDTO save(ProductsDTO productsDTO) {
        validationsProducts.verificationExistingProduct(productsRepository, productsDTO.getId(), productsDTO);
        try {
            validationsProducts.validatePrice(productsDTO.getPrice());
            Products product = mappers.toEntity(productsDTO);
            Products saved = productsRepository.save(product);
            return mappers.toDTO(saved);
        } catch (Exception e) {
            throw new ProductsException();
        }
    }

    @Transactional
    public ProductsDTO update(Long id, ProductsDTO productsDTO) {
        Products existingProduct = productsRepository.findById(productsDTO.getId())
                .orElseThrow(() -> new ProductsNotFoundException("Product not found with Id: " + productsDTO.getId()));

        if (!existingProduct.getName().equals(productsDTO.getName())) {
            validationsProducts.verificationExistingProduct(productsRepository, id, productsDTO);
        }
        try {
            validationsProducts.validatePrice(productsDTO.getPrice());

            existingProduct.setName(productsDTO.getName());
            existingProduct.setPrice(productsDTO.getPrice());
            existingProduct.setStock(productsDTO.getStock());

            Products updated = productsRepository.save(existingProduct);
            return mappers.toDTO(updated);
        } catch (Exception e) {
            throw new ProductsException();
        }
    }

    @Transactional
    public void delete(Long id) {
        if (!productsRepository.existsById(id)) {
            throw new ProductsNotFoundException();
        }
        productsRepository.deleteById(id);
    }

    @Transactional
    public void decreaseStock(Long productId, Integer quantity) {
        Products product = productsRepository.findById(productId)
                .orElseThrow(() -> new ProductsNotFoundException("Product not found with Id: " + productId));

        if (product.getStock() < quantity) {
            throw new br.com.thallysprojetos.desafio2.exceptions.products.InsufficientStockException(
                    String.format("Insufficient stock for product ‘%s’. Available: %d, Requested: %d",
                            product.getName(), product.getStock(), quantity)
            );
        }

        product.setStock(product.getStock() - quantity);
        productsRepository.save(product);
    }

}