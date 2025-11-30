package br.com.thallysprojetos.desafio2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.thallysprojetos.desafio2.models.Orders;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
}