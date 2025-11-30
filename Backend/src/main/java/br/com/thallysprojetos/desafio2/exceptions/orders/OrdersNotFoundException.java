package br.com.thallysprojetos.desafio2.exceptions.orders;

public class OrdersNotFoundException extends RuntimeException {

    public OrdersNotFoundException() {
        super("Order not found with Id");
    }

    public OrdersNotFoundException(String message) {
        super(message);
    }

}