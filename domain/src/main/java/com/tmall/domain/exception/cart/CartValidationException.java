package com.tmall.domain.exception.cart;

import java.io.Serial;

public class CartValidationException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -3882925037497597538L;

    public CartValidationException(String message) {
        super(message);
    }
}