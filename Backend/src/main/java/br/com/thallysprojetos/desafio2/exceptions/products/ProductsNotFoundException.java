package br.com.thallysprojetos.desafio2.exceptions.products;

public class ProductsNotFoundException extends RuntimeException {

    public ProductsNotFoundException() {
        super("Product not found with id");
    }

    public ProductsNotFoundException(String message) {
        super(message);
    }

}