package br.com.thallysprojetos.desafio2.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.thallysprojetos.desafio2.dtos.ProductsDTO;
import br.com.thallysprojetos.desafio2.services.ProductsService;

import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
@Tag(name = "Products", description = "API para gerenciamento de produtos")
public class ProductsController {

    private final ProductsService service;

    @GetMapping
    @Operation(summary = "Listar todos os produtos")
    public ResponseEntity<List<ProductsDTO>> getAllProducts() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto por ID")
    public ResponseEntity<ProductsDTO> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar novo produto")
    public ResponseEntity<ProductsDTO> createProduct(@Valid @RequestBody ProductsDTO dto) {
        ProductsDTO created = service.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar produto existente")
    public ResponseEntity<ProductsDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductsDTO dto) {
        dto.setId(id);
        ProductsDTO updated = service.update(dto);
        return ResponseEntity.ok().body(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletar produto")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}