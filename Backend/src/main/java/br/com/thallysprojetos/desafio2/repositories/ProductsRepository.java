package br.com.thallysprojetos.desafio2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.thallysprojetos.desafio2.models.Products;

public interface ProductsRepository extends JpaRepository<Products, Long> {
}