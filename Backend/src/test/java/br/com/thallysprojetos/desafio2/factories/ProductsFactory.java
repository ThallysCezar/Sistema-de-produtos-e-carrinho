package br.com.thallysprojetos.desafio2.factories;

import br.com.thallysprojetos.desafio2.dtos.ProductsDTO;
import br.com.thallysprojetos.desafio2.models.Products;

import java.math.BigDecimal;

public class ProductsFactory {

    public static Products createProduct() {
        return createProduct(1L, "Notebook Dell", "3500.00", 10);
    }

    public static Products createProduct(Long id, String name, String price, Integer stock) {
        Products product = new Products();
        product.setId(id);
        product.setName(name);
        product.setPrice(new BigDecimal(price));
        product.setStock(stock);
        return product;
    }

    public static Products createNotebook() {
        return createProduct(1L, "Notebook Dell", "3500.00", 10);
    }

    public static Products createMouse() {
        return createProduct(2L, "Mouse Gamer", "150.00", 50);
    }

    public static Products createKeyboard() {
        return createProduct(3L, "Teclado Mecânico", "450.00", 30);
    }

    public static Products createProductWithoutStock() {
        return createProduct(1L, "Notebook Dell", "3500.00", 0);
    }

    public static Products createProductWithLowStock() {
        return createProduct(1L, "Notebook Dell", "3500.00", 3);
    }

    public static ProductsDTO createProductDTO() {
        return createProductDTO(1L, "Notebook Dell", "3500.00", 10);
    }

    public static ProductsDTO createProductDTO(Long id, String name, String price, Integer stock) {
        ProductsDTO productDTO = new ProductsDTO();
        productDTO.setId(id);
        productDTO.setName(name);
        productDTO.setPrice(new BigDecimal(price));
        productDTO.setStock(stock);
        return productDTO;
    }

    public static ProductsDTO createNotebookDTO() {
        return createProductDTO(1L, "Notebook Dell", "3500.00", 10);
    }

    public static ProductsDTO createMouseDTO() {
        return createProductDTO(2L, "Mouse Gamer", "150.00", 50);
    }

    public static ProductsDTO createKeyboardDTO() {
        return createProductDTO(3L, "Teclado Mecânico", "450.00", 30);
    }

    public static ProductsDTO createProductDTOWithNegativePrice() {
        return createProductDTO(1L, "Produto Inválido", "-100.00", 10);
    }

    public static ProductsDTO createProductDTOWithoutId() {
        return createProductDTO(null, "Novo Produto", "1500.00", 20);
    }

    public static ProductsDTO createUpdatedNotebookDTO() {
        return createProductDTO(1L, "Notebook Dell Updated", "3800.00", 15);
    }

    public static java.util.List<Products> createProductsList() {
        return java.util.List.of(createNotebook(), createMouse());
    }

    public static java.util.List<ProductsDTO> createProductsDTOList() {
        return java.util.List.of(createNotebookDTO(), createMouseDTO());
    }

}
