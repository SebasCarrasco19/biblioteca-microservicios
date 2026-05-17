package com.example.fine_service.ExceptionFine;

public class FineNotFoundException extends RuntimeException {

    public FineNotFoundException(Long id) {
        super("No se encontró la multa con ID: " + id);
    }
}