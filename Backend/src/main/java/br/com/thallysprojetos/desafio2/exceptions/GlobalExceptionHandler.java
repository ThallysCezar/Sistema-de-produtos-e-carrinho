package br.com.thallysprojetos.desafio2.exceptions;

import br.com.thallysprojetos.desafio2.exceptions.orders.OrdersException;
import br.com.thallysprojetos.desafio2.exceptions.orders.OrdersNotFoundException;
import br.com.thallysprojetos.desafio2.exceptions.products.InsufficientStockException;
import br.com.thallysprojetos.desafio2.exceptions.products.ProductsAlreadyExistException;
import br.com.thallysprojetos.desafio2.exceptions.products.ProductsException;
import br.com.thallysprojetos.desafio2.exceptions.products.ProductsNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ProductsNotFoundException.class, OrdersNotFoundException.class})
    public ResponseEntity<ErrorMessage> handleNotFoundException(Exception ex, HttpServletRequest request) {
        log.warn("Product or Orders not found: {}", ex.getMessage());
        return buildErrorResponse(ex, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ProductsAlreadyExistException.class})
    public ResponseEntity<ErrorMessage> handleAlreadyExistException(Exception ex, HttpServletRequest request) {
        log.warn("Product already exist: {}", ex.getMessage());
        return buildErrorResponse(ex, request, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ErrorMessage> handleIllegalArgumentException(Exception ex, HttpServletRequest request) {
        log.warn("Illegal argument: {}", ex.getMessage());
        return buildErrorResponse(ex, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InsufficientStockException.class})
    public ResponseEntity<ErrorMessage> handleInsufficientStockException(Exception ex, HttpServletRequest request) {
        log.warn("Insufficient Stock: {}", ex.getMessage());
        return buildErrorResponse(ex, request, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({ProductsException.class, OrdersException.class})
    public ResponseEntity<ErrorMessage> handleGeneralException(Exception ex, HttpServletRequest request) {
        log.error("Unexpected error: {}", ex.getMessage(), ex);
        String message = "An internal server error has occurred. Please try again later.";
        return buildErrorResponse(new Exception(message), request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public ResponseEntity<ErrorMessage> handleDataIntegrityViolationException(Exception ex, HttpServletRequest request) {
        log.error("Data integrity violation: {}", ex.getMessage(), ex);
        String message = "Data integrity error. Please check the provided data and try again.";
        return buildErrorResponse(new Exception(message), request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleGenericException(Exception ex, HttpServletRequest request) {
        log.error("Unexpected server error: {}", ex.getMessage(), ex);
        String message = "An unexpected error occurred. Please contact support if the problem persists.";
        return buildErrorResponse(new Exception(message), request, HttpStatus.INTERNAL_SERVER_ERROR);
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

    private ResponseEntity<ErrorMessage> buildErrorResponse(Exception ex, HttpServletRequest request, HttpStatus status) {
        ErrorMessage errorMessage = new ErrorMessage(request, status, ex.getMessage());
        return ResponseEntity
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorMessage);
    }

}