package br.com.thallysprojetos.desafio2.exceptions.products;

public class ProductsException extends RuntimeException {

    public ProductsException() {
        super("General error ");
    }

    public ProductsException(String message) {
        super(message);
    }

}
