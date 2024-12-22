package com.tmall.domain.exception.item;

import lombok.RequiredArgsConstructor;

import java.io.Serial;

@RequiredArgsConstructor
public class ItemNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -5755701146909059520L;

    public ItemNotFoundException(String message) {
        super(message);
    }
}
