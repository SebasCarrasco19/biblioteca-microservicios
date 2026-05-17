package com.biblioteca.book_service.exception;

public class RemoteCategoryNotFoundException extends RuntimeException {

    public RemoteCategoryNotFoundException(Long categoryId) {
        super("No se encontró la categoría con ID: " + categoryId);
    }
}
