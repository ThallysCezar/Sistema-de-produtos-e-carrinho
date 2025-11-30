package br.com.thallysprojetos.desafio2.exceptions.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ModelException extends ResponseStatusException {

    public ModelException(){
        super(HttpStatus.INTERNAL_SERVER_ERROR, "Erro geral model");
    }

    public ModelException(String message){
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

}
