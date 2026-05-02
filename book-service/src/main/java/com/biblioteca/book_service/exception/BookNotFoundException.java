package com.biblioteca.book_service.exception;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(Long id) {
        super("No se encontro el libro con id: " + id);
    }

    public BookNotFoundException(String isbn) {
        super("No se encontro el libro con isbn: " + isbn);
    }
}
