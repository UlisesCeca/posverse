package com.ulises.posverse.exceptions;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(final Long id) {
        super("Product with ID " + id + " not found.");
    }

    public ProductNotFoundException(final String message) {
        super(message);
    }
}
