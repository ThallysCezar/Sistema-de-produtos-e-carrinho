package br.com.thallysprojetos.desafio2.exceptions.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ModelAlreadyExistException extends ResponseStatusException {

    public ModelAlreadyExistException(){
        super(HttpStatus.CONFLICT, "Já existe um usuário com esse email");
    }

    public ModelAlreadyExistException(String message){
        super(HttpStatus.CONFLICT, message);
    }

}