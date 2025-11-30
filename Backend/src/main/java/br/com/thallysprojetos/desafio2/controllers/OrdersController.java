package br.com.thallysprojetos.desafio2.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.thallysprojetos.desafio2.dtos.CheckoutRequestDTO;
import br.com.thallysprojetos.desafio2.dtos.OrdersDTO;
import br.com.thallysprojetos.desafio2.services.OrdersService;

import java.util.List;

@RestController
@RequestMapping("/cart")
@AllArgsConstructor
@Tag(name = "Cart", description = "API para gerenciamento de carrinho e pedidos")
public class OrdersController {

    private final OrdersService ordersService;

    @PostMapping("/checkout")
    @Operation(summary = "Realizar checkout do carrinho", 
               description = "Cria um pedido com os itens do carrinho, valida estoque e diminui quantidade disponível")
    public ResponseEntity<OrdersDTO> checkout(@Valid @RequestBody CheckoutRequestDTO checkoutRequest) {
        OrdersDTO order = ordersService.checkout(checkoutRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @GetMapping("/orders")
    @Operation(summary = "Listar todos os pedidos")
    public ResponseEntity<List<OrdersDTO>> getAllOrders() {
        return ResponseEntity.ok().body(ordersService.findAll());
    }

    @GetMapping("/orders/{id}")
    @Operation(summary = "Buscar pedido por ID")
    public ResponseEntity<OrdersDTO> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok().body(ordersService.findById(id));
    }

}