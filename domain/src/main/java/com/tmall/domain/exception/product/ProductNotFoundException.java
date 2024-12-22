package com.tmall.domain.exception.product;

import java.io.Serial;

public class ProductNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 8103273203622861305L;

    public ProductNotFoundException(String message) {
        super(message);
    }
}