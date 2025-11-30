package br.com.thallysprojetos.desafio2.services.impl;

import br.com.thallysprojetos.desafio2.dtos.CheckoutRequestDTO;
import br.com.thallysprojetos.desafio2.dtos.OrdersDTO;
import br.com.thallysprojetos.desafio2.exceptions.orders.OrdersNotFoundException;
import br.com.thallysprojetos.desafio2.exceptions.products.InsufficientStockException;
import br.com.thallysprojetos.desafio2.exceptions.products.ProductsNotFoundException;
import br.com.thallysprojetos.desafio2.factories.CheckoutFactory;
import br.com.thallysprojetos.desafio2.factories.OrdersFactory;
import br.com.thallysprojetos.desafio2.factories.ProductsFactory;
import br.com.thallysprojetos.desafio2.mappers.OrdersMappers;
import br.com.thallysprojetos.desafio2.messaging.OrderEventPublisher;
import br.com.thallysprojetos.desafio2.models.Orders;
import br.com.thallysprojetos.desafio2.models.Products;
import br.com.thallysprojetos.desafio2.repositories.OrdersRepository;
import br.com.thallysprojetos.desafio2.repositories.ProductsRepository;
import br.com.thallysprojetos.desafio2.services.ProductsService;
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
@DisplayName("OrdersServiceImpl Tests")
class OrdersServiceImplTest {

    @Mock
    private OrdersRepository ordersRepository;

    @Mock
    private ProductsRepository productsRepository;

    @Mock
    private ProductsService productsService;

    @Mock
    private OrdersMappers ordersMappers;

    @Mock
    private OrderEventPublisher orderEventPublisher;

    @InjectMocks
    private OrdersServiceImpl ordersService;

    private Products product1;
    private Products product2;
    private Orders order;
    private OrdersDTO orderDTO;
    private CheckoutRequestDTO checkoutRequest;

    @BeforeEach
    void setUp() {
        product1 = ProductsFactory.createNotebook();
        product2 = ProductsFactory.createMouse();
        order = OrdersFactory.createOrder();
        orderDTO = OrdersFactory.createOrderDTO();
        checkoutRequest = CheckoutFactory.createCheckoutRequest();
    }

    @Test
    @DisplayName("Should process checkout successfully")
    void shouldProcessCheckoutSuccessfully() {
        when(productsRepository.findById(1L)).thenReturn(Optional.of(product1));
        when(productsRepository.findById(2L)).thenReturn(Optional.of(product2));
        when(ordersRepository.save(any(Orders.class))).thenReturn(order);
        when(ordersMappers.toDTO(any(Orders.class))).thenReturn(orderDTO);
        doNothing().when(productsService).decreaseStock(anyLong(), anyInt());

        OrdersDTO result = ordersService.checkout(checkoutRequest);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTotal()).isEqualByComparingTo(new BigDecimal("3800.00"));

        verify(productsRepository, times(1)).findById(1L);
        verify(productsRepository, times(1)).findById(2L);
        verify(ordersRepository, times(1)).save(any(Orders.class));
        verify(productsService, times(1)).decreaseStock(1L, 1);
        verify(productsService, times(1)).decreaseStock(2L, 2);
        verify(ordersMappers, times(1)).toDTO(any(Orders.class));
    }

    @Test
    @DisplayName("Should calculate total correctly with multiple items")
    void shouldCalculateTotalCorrectly() {
        when(productsRepository.findById(1L)).thenReturn(Optional.of(product1));
        when(productsRepository.findById(2L)).thenReturn(Optional.of(product2));
        when(ordersRepository.save(any(Orders.class))).thenAnswer(invocation -> {
            Orders savedOrder = invocation.getArgument(0);
            assertThat(savedOrder.getTotal()).isEqualByComparingTo(new BigDecimal("3800.00")); // 3500 + (150 * 2)
            return savedOrder;
        });
        when(ordersMappers.toDTO(any(Orders.class))).thenReturn(orderDTO);
        doNothing().when(productsService).decreaseStock(anyLong(), anyInt());

        OrdersDTO result = ordersService.checkout(checkoutRequest);

        assertThat(result).isNotNull();
        verify(ordersRepository, times(1)).save(any(Orders.class));
    }

    @Test
    @DisplayName("Should throw exception when product not found during checkout")
    void shouldThrowExceptionWhenProductNotFoundDuringCheckout() {
        when(productsRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> ordersService.checkout(checkoutRequest))
                .isInstanceOf(ProductsNotFoundException.class)
                .hasMessageContaining("Product not found with Id: 1");

        verify(productsRepository, times(1)).findById(1L);
        verify(ordersRepository, never()).save(any());
        verify(productsService, never()).decreaseStock(anyLong(), anyInt());
    }

    @Test
    @DisplayName("Should throw exception when insufficient stock during checkout")
    void shouldThrowExceptionWhenInsufficientStock() {
        Products productWithoutStock = ProductsFactory.createProductWithoutStock();

        when(productsRepository.findById(1L)).thenReturn(Optional.of(productWithoutStock));

        assertThatThrownBy(() -> ordersService.checkout(checkoutRequest))
                .isInstanceOf(InsufficientStockException.class)
                .hasMessageContaining("Insufficient stock for product")
                .hasMessageContaining("Notebook Dell")
                .hasMessageContaining("Available: 0")
                .hasMessageContaining("Requested: 1");

        verify(productsRepository, times(1)).findById(1L);
        verify(ordersRepository, never()).save(any());
        verify(productsService, never()).decreaseStock(anyLong(), anyInt());
    }

    @Test
    @DisplayName("Should throw exception when requested quantity exceeds stock")
    void shouldThrowExceptionWhenRequestedQuantityExceedsStock() {
        CheckoutRequestDTO excessiveRequest = CheckoutFactory.createCheckoutRequestWithExcessiveQuantity();

        when(productsRepository.findById(1L)).thenReturn(Optional.of(product1));

        assertThatThrownBy(() -> ordersService.checkout(excessiveRequest))
                .isInstanceOf(InsufficientStockException.class)
                .hasMessageContaining("Insufficient stock for product")
                .hasMessageContaining("Notebook Dell")
                .hasMessageContaining("Available: 10")
                .hasMessageContaining("Requested: 20");

        verify(productsRepository, times(1)).findById(1L);
        verify(ordersRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should return all orders successfully")
    void shouldReturnAllOrders() {
        List<Orders> ordersList = OrdersFactory.createOrdersList();
        List<OrdersDTO> ordersDTOList = OrdersFactory.createOrdersDTOList();

        when(ordersRepository.findAll()).thenReturn(ordersList);
        when(ordersMappers.toListDTO(ordersList)).thenReturn(ordersDTOList);

        List<OrdersDTO> result = ordersService.findAll();

        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(1).getId()).isEqualTo(2L);

        verify(ordersRepository, times(1)).findAll();
        verify(ordersMappers, times(1)).toListDTO(ordersList);
    }

    @Test
    @DisplayName("Should return order by ID successfully")
    void shouldReturnOrderById() {
        when(ordersRepository.findById(1L)).thenReturn(Optional.of(order));
        when(ordersMappers.toDTO(order)).thenReturn(orderDTO);

        OrdersDTO result = ordersService.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTotal()).isEqualByComparingTo(new BigDecimal("3800.00"));

        verify(ordersRepository, times(1)).findById(1L);
        verify(ordersMappers, times(1)).toDTO(order);
    }

    @Test
    @DisplayName("Should throw exception when order not found by ID")
    void shouldThrowExceptionWhenOrderNotFoundById() {
        when(ordersRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> ordersService.findById(999L))
                .isInstanceOf(OrdersNotFoundException.class)
                .hasMessageContaining("Order not found with Id: 999");

        verify(ordersRepository, times(1)).findById(999L);
        verify(ordersMappers, never()).toDTO(any());
    }

    @Test
    @DisplayName("Should process checkout with single item")
    void shouldProcessCheckoutWithSingleItem() {
        CheckoutRequestDTO singleItemRequest = CheckoutFactory.createSingleItemCheckoutRequest();

        when(productsRepository.findById(1L)).thenReturn(Optional.of(product1));
        when(ordersRepository.save(any(Orders.class))).thenAnswer(invocation -> {
            Orders savedOrder = invocation.getArgument(0);
            assertThat(savedOrder.getTotal()).isEqualByComparingTo(new BigDecimal("7000.00")); // 3500 * 2
            return savedOrder;
        });
        when(ordersMappers.toDTO(any(Orders.class))).thenReturn(orderDTO);
        doNothing().when(productsService).decreaseStock(anyLong(), anyInt());

        OrdersDTO result = ordersService.checkout(singleItemRequest);

        assertThat(result).isNotNull();
        verify(productsRepository, times(1)).findById(1L);
        verify(productsService, times(1)).decreaseStock(1L, 2);
    }

    @Test
    @DisplayName("Should decrease stock only after order is saved")
    void shouldDecreaseStockOnlyAfterOrderSaved() {
        when(productsRepository.findById(1L)).thenReturn(Optional.of(product1));
        when(productsRepository.findById(2L)).thenReturn(Optional.of(product2));
        when(ordersRepository.save(any(Orders.class))).thenReturn(order);
        when(ordersMappers.toDTO(any(Orders.class))).thenReturn(orderDTO);
        doNothing().when(productsService).decreaseStock(anyLong(), anyInt());

        ordersService.checkout(checkoutRequest);

        verify(ordersRepository, times(1)).save(any(Orders.class));
        verify(productsService, times(1)).decreaseStock(1L, 1);
        verify(productsService, times(1)).decreaseStock(2L, 2);
    }

}