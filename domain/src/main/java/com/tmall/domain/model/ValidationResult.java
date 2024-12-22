package com.tmall.domain.model;

public record ValidationResult(boolean isValid, String errorMessage) {

    public static ValidationResult valid() {
        return new ValidationResult(true, null);
    }

    public static <T extends RuntimeException> ValidationResult invalid(String errorMessage, Class<T> exceptionType) {
        try {
            throw exceptionType.getConstructor(String.class).newInstance(errorMessage);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to create exception of type: " + exceptionType.getName(), e);
        }
    }
}