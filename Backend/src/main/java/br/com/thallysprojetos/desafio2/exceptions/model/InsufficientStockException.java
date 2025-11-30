package br.com.thallysprojetos.desafio2.exceptions.model;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String message) {
        super(message);
    }
}
