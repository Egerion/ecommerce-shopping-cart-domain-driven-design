package com.tmall.domain.exception.cart;

import java.io.Serial;

public class CartNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 2135990265523015761L;

    public CartNotFoundException(String message) {
        super(message);
    }
}