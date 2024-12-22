package com.tmall.domain.exception.handle;

import com.tmall.domain.exception.cart.CartNotFoundException;
import com.tmall.domain.exception.cart.CartValidationException;
import com.tmall.domain.exception.item.ItemNotFoundException;
import com.tmall.domain.exception.model.ErrorResponse;
import com.tmall.domain.exception.product.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.Serial;

@ControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -4489514045063589332L;

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        ErrorResponse response = new ErrorResponse(false, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCartNotFoundException(CartNotFoundException ex) {
        ErrorResponse response = new ErrorResponse(false, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CartValidationException.class)
    public ResponseEntity<ErrorResponse> handleCartNotFoundException(CartValidationException ex) {
        ErrorResponse response = new ErrorResponse(false, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCartNotFoundException(ItemNotFoundException ex) {
        ErrorResponse response = new ErrorResponse(false, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCartNotFoundException(ProductNotFoundException ex) {
        ErrorResponse response = new ErrorResponse(false, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}