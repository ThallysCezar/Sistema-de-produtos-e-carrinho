package br.com.thallysprojetos.desafio2.exceptions.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ModelNotFoundException extends ResponseStatusException {

    public ModelNotFoundException(){
        super(HttpStatus.UNPROCESSABLE_ENTITY, "Usuario não encontrado com esse id");
    }

    public ModelNotFoundException(String message){
        super(HttpStatus.UNPROCESSABLE_ENTITY, message);
    }

}