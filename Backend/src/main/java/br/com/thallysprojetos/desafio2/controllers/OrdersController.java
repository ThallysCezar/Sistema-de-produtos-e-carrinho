package br.com.thallysprojetos.desafio2.controllers;

import br.com.thallysprojetos.desafio2.dtos.CheckoutRequestDTO;
import br.com.thallysprojetos.desafio2.dtos.OrdersDTO;
import br.com.thallysprojetos.desafio2.services.OrdersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@AllArgsConstructor
@Tag(name = "Cart", description = "API for cart and order management")
public class OrdersController {

    private final OrdersService ordersService;

    @PostMapping("/checkout")
    @Operation(summary = "Proceed to checkout",
            description = "Creates an order with the items in the cart, validates inventory, and decreases available quantity.")
    public ResponseEntity<OrdersDTO> checkout(@Valid @RequestBody CheckoutRequestDTO checkoutRequest) {
        OrdersDTO order = ordersService.checkout(checkoutRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @GetMapping("/orders")
    @Operation(summary = "List all requests")
    public ResponseEntity<List<OrdersDTO>> getAllOrders() {
        return ResponseEntity.ok().body(ordersService.findAll());
    }

    @GetMapping("/orders/{id}")
    @Operation(summary = "Search order by ID")
    public ResponseEntity<OrdersDTO> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok().body(ordersService.findById(id));
    }

}