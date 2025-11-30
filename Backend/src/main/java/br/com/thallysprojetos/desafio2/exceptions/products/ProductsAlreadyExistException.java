package br.com.thallysprojetos.desafio2.exceptions.products;

public class ProductsAlreadyExistException extends RuntimeException {

    public ProductsAlreadyExistException() {
        super("There is already a product with the name");
    }

    public ProductsAlreadyExistException(String message) {
        super(message);
    }

}