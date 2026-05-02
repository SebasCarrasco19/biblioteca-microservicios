package com.biblioteca.book_service.exception;

public class DuplicateIsbnException extends RuntimeException {

    public DuplicateIsbnException(String isbn) {
        super("Ya existe un libro con isbn: " + isbn);
    }
}
