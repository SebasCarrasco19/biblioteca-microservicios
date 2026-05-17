package com.biblioteca.book_service.exception;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(Long id) {
        super("No se encontró el libro con ID: " + id);
    }

    public BookNotFoundException(String isbn) {
        super("No se encontró el libro con ISBN: " + isbn);
    }
}
