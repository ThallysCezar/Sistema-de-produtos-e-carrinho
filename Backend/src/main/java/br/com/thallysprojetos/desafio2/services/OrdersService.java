package br.com.thallysprojetos.desafio2.services;

import br.com.thallysprojetos.desafio2.dtos.CheckoutRequestDTO;
import br.com.thallysprojetos.desafio2.dtos.OrdersDTO;

import java.util.List;

public interface OrdersService {

    OrdersDTO checkout(CheckoutRequestDTO checkoutRequest);

    List<OrdersDTO> findAll();

    OrdersDTO findById(Long id);

}