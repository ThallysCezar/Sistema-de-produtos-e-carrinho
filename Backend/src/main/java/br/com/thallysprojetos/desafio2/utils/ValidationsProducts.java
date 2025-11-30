package br.com.thallysprojetos.desafio2.utils;

import br.com.thallysprojetos.desafio2.dtos.ProductsDTO;
import br.com.thallysprojetos.desafio2.exceptions.products.ProductsAlreadyExistException;
import br.com.thallysprojetos.desafio2.exceptions.products.ProductsException;
import br.com.thallysprojetos.desafio2.models.Products;
import br.com.thallysprojetos.desafio2.repositories.ProductsRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class ValidationsProducts {

    public void validatePrice(BigDecimal price) {
        if (price != null && price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
    }

    public void verificationExistingProduct(ProductsRepository productsRepository, Long id, ProductsDTO dto) {
        try {
            Optional<Products> productWithSameName = productsRepository.findByName(dto.getName());
            if (productWithSameName.isPresent()) {
                Long existingProductId = productWithSameName.get().getId();
                if (id == null || !existingProductId.equals(id)) {
                    throw new ProductsAlreadyExistException(
                            "There is already a product with the name: " + dto.getName()
                    );
                }
            }
        } catch (ProductsAlreadyExistException e) {
            throw e;
        } catch (Exception e) {
            throw new ProductsException("Error validating existing product: " + e.getMessage());
        }
    }

}