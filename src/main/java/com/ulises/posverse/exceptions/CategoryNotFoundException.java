package com.ulises.posverse.exceptions;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(final Long id) {
        super("Category with ID " + id + " not found.");
    }

    public CategoryNotFoundException(final String message) {
        super(message);
    }
}
