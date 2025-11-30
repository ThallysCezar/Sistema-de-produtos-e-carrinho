package br.com.thallysprojetos.desafio2.exceptions.orders;

public class OrdersException extends RuntimeException {

    public OrdersException() {
        super("General error ");
    }

    public OrdersException(String message) {
        super(message);
    }

}
