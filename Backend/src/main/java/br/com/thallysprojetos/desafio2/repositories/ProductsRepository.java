package br.com.thallysprojetos.desafio2.repositories;

import br.com.thallysprojetos.desafio2.models.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductsRepository extends JpaRepository<Products, Long> {

    Optional<Products> findByName(String name);

}