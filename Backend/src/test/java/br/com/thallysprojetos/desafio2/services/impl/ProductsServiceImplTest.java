package br.com.thallysprojetos.desafio2.services.impl;

import br.com.thallysprojetos.desafio2.dtos.ProductsDTO;
import br.com.thallysprojetos.desafio2.exceptions.products.InsufficientStockException;
import br.com.thallysprojetos.desafio2.exceptions.products.ProductsAlreadyExistException;
import br.com.thallysprojetos.desafio2.exceptions.products.ProductsException;
import br.com.thallysprojetos.desafio2.exceptions.products.ProductsNotFoundException;
import br.com.thallysprojetos.desafio2.factories.ProductsFactory;
import br.com.thallysprojetos.desafio2.mappers.ProductsMappers;
import br.com.thallysprojetos.desafio2.models.Products;
import br.com.thallysprojetos.desafio2.repositories.ProductsRepository;
import br.com.thallysprojetos.desafio2.utils.ValidationsProducts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProductsServiceImpl Tests")
class ProductsServiceImplTest {

    @Mock
    private ProductsRepository productsRepository;

    @Mock
    private ProductsMappers mappers;

    @Mock
    private ValidationsProducts validationsProducts;

    @InjectMocks
    private ProductsServiceImpl productsService;

    private Products product;
    private ProductsDTO productDTO;

    @BeforeEach
    void setUp() {
        product = ProductsFactory.createNotebook();
        productDTO = ProductsFactory.createNotebookDTO();
    }

    @Test
    @DisplayName("Should return all products successfully")
    void shouldReturnAllProducts() {
        List<Products> productsList = ProductsFactory.createProductsList();
        List<ProductsDTO> productsDTOList = ProductsFactory.createProductsDTOList();

        when(productsRepository.findAll()).thenReturn(productsList);
        when(mappers.toListDTO(productsList)).thenReturn(productsDTOList);

        List<ProductsDTO> result = productsService.findAll();

        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("Notebook Dell");
        assertThat(result.get(1).getName()).isEqualTo("Mouse Gamer");

        verify(productsRepository, times(1)).findAll();
        verify(mappers, times(1)).toListDTO(productsList);
    }

    @Test
    @DisplayName("Should return product by ID successfully")
    void shouldReturnProductById() {
        when(productsRepository.findById(1L)).thenReturn(Optional.of(product));
        when(mappers.toDTO(product)).thenReturn(productDTO);

        ProductsDTO result = productsService.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Notebook Dell");
        assertThat(result.getPrice()).isEqualByComparingTo(new BigDecimal("3500.00"));
        assertThat(result.getStock()).isEqualTo(10);

        verify(productsRepository, times(1)).findById(1L);
        verify(mappers, times(1)).toDTO(product);
    }

    @Test
    @DisplayName("Should throw ProductsNotFoundException when product not found by ID")
    void shouldThrowExceptionWhenProductNotFoundById() {
        when(productsRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productsService.findById(999L))
                .isInstanceOf(ProductsNotFoundException.class)
                .hasMessageContaining("Product not found with Id: 999");

        verify(productsRepository, times(1)).findById(999L);
        verify(mappers, never()).toDTO(any());
    }

    @Test
    @DisplayName("Should save new product successfully")
    void shouldSaveNewProduct() {
        when(mappers.toEntity(productDTO)).thenReturn(product);
        when(productsRepository.save(product)).thenReturn(product);
        when(mappers.toDTO(product)).thenReturn(productDTO);
        doNothing().when(validationsProducts).verificationExistingProduct(any(), any(), any());
        doNothing().when(validationsProducts).validatePrice(any());

        ProductsDTO result = productsService.save(productDTO);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Notebook Dell");

        verify(validationsProducts, times(1)).verificationExistingProduct(any(), any(), any());
        verify(validationsProducts, times(1)).validatePrice(any());
        verify(productsRepository, times(1)).save(product);
    }

    @Test
    @DisplayName("Should throw exception when saving product with existing name")
    void shouldThrowExceptionWhenSavingProductWithExistingName() {
        doThrow(new ProductsAlreadyExistException("There is already a product with the name: Notebook Dell"))
                .when(validationsProducts).verificationExistingProduct(any(), any(), any());

        assertThatThrownBy(() -> productsService.save(productDTO))
                .isInstanceOf(ProductsAlreadyExistException.class)
                .hasMessageContaining("There is already a product with the name: Notebook Dell");

        verify(validationsProducts, times(1)).verificationExistingProduct(any(), any(), any());
        verify(productsRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw exception when saving product with negative price")
    void shouldThrowExceptionWhenSavingProductWithNegativePrice() {
        ProductsDTO invalidDTO = ProductsFactory.createProductDTOWithNegativePrice();

        doNothing().when(validationsProducts).verificationExistingProduct(any(), any(), any());
        doThrow(new IllegalArgumentException("Price cannot be negative"))
                .when(validationsProducts).validatePrice(any());

        assertThatThrownBy(() -> productsService.save(invalidDTO))
                .isInstanceOf(ProductsException.class);

        verify(validationsProducts, times(1)).validatePrice(any());
        verify(productsRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should update existing product successfully")
    void shouldUpdateExistingProduct() {
        ProductsDTO updatedDTO = ProductsFactory.createUpdatedNotebookDTO();

        when(productsRepository.findById(1L)).thenReturn(Optional.of(product));
        doNothing().when(validationsProducts).verificationExistingProduct(any(), any(), any());
        doNothing().when(validationsProducts).validatePrice(any());
        when(productsRepository.save(product)).thenReturn(product);
        when(mappers.toDTO(product)).thenReturn(updatedDTO);

        ProductsDTO result = productsService.update(1L, updatedDTO);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Notebook Dell Updated");
        assertThat(result.getPrice()).isEqualByComparingTo(new BigDecimal("3800.00"));
        assertThat(result.getStock()).isEqualTo(15);

        verify(productsRepository, times(1)).findById(1L);
        verify(validationsProducts, times(1)).validatePrice(any());
        verify(productsRepository, times(1)).save(product);
    }

    @Test
    @DisplayName("Should throw exception when updating non-existing product")
    void shouldThrowExceptionWhenUpdatingNonExistingProduct() {
        when(productsRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productsService.update(999L, productDTO))
                .isInstanceOf(ProductsNotFoundException.class)
                .hasMessageContaining("Product not found with Id: 1");

        verify(productsRepository, times(1)).findById(1L);
        verify(productsRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should delete product successfully")
    void shouldDeleteProduct() {
        when(productsRepository.existsById(1L)).thenReturn(true);
        doNothing().when(productsRepository).deleteById(1L);

        productsService.delete(1L);

        verify(productsRepository, times(1)).existsById(1L);
        verify(productsRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existing product")
    void shouldThrowExceptionWhenDeletingNonExistingProduct() {
        when(productsRepository.existsById(anyLong())).thenReturn(false);

        assertThatThrownBy(() -> productsService.delete(999L))
                .isInstanceOf(ProductsNotFoundException.class);

        verify(productsRepository, times(1)).existsById(999L);
        verify(productsRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("Should decrease stock successfully")
    void shouldDecreaseStock() {
        when(productsRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productsRepository.save(product)).thenReturn(product);

        productsService.decreaseStock(1L, 3);

        assertThat(product.getStock()).isEqualTo(7);

        verify(productsRepository, times(1)).findById(1L);
        verify(productsRepository, times(1)).save(product);
    }

    @Test
    @DisplayName("Should throw exception when decreasing stock with insufficient quantity")
    void shouldThrowExceptionWhenDecreasingStockWithInsufficientQuantity() {
        when(productsRepository.findById(1L)).thenReturn(Optional.of(product));

        assertThatThrownBy(() -> productsService.decreaseStock(1L, 15))
                .isInstanceOf(InsufficientStockException.class)
                .hasMessageContaining("Insufficient stock for product")
                .hasMessageContaining("Notebook Dell")
                .hasMessageContaining("Available: 10")
                .hasMessageContaining("Requested: 15");

        verify(productsRepository, times(1)).findById(1L);
        verify(productsRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw exception when decreasing stock of non-existing product")
    void shouldThrowExceptionWhenDecreasingStockOfNonExistingProduct() {
        when(productsRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productsService.decreaseStock(999L, 5))
                .isInstanceOf(ProductsNotFoundException.class)
                .hasMessageContaining("Product not found with Id: 999");

        verify(productsRepository, times(1)).findById(999L);
        verify(productsRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should decrease stock to zero when exact quantity is requested")
    void shouldDecreaseStockToZero() {
        when(productsRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productsRepository.save(product)).thenReturn(product);

        productsService.decreaseStock(1L, 10);

        assertThat(product.getStock()).isEqualTo(0);

        verify(productsRepository, times(1)).findById(1L);
        verify(productsRepository, times(1)).save(product);
    }

}