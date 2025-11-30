package br.com.thallysprojetos.desafio2.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.thallysprojetos.desafio2.exceptions.model.ModelAlreadyExistException;
import br.com.thallysprojetos.desafio2.exceptions.model.ModelException;
import br.com.thallysprojetos.desafio2.exceptions.model.ModelNotFoundException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({ModelNotFoundException.class})
    public ResponseEntity<ErrorMessage> handleNotFoundException(Exception ex, HttpServletRequest request) {
        return handleException(ex, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ModelAlreadyExistException.class})
    public ResponseEntity<ErrorMessage> handleAlreadyExistException(Exception ex, HttpServletRequest request) {
        return handleException(ex, request, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ErrorMessage> handleIllegalArgumentException(Exception ex, HttpServletRequest request) {
        return handleException(ex, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({br.com.thallysprojetos.desafio2.exceptions.model.InsufficientStockException.class})
    public ResponseEntity<ErrorMessage> handleInsufficientStockException(Exception ex, HttpServletRequest request) {
        return handleException(ex, request, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({ModelException.class})
    public ResponseEntity<ErrorMessage> handleGeneralException(Exception ex, HttpServletRequest request) {
        return handleException(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    private ResponseEntity<ErrorMessage> handleException(Exception ex, HttpServletRequest request, HttpStatus status) {
        return ResponseEntity
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, status, ex.getMessage()));
    }

}