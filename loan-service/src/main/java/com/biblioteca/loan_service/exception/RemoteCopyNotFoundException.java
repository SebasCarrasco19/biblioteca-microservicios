package com.biblioteca.loan_service.exception;

public class RemoteCopyNotFoundException extends RuntimeException {

    public RemoteCopyNotFoundException(Long copyId) {
        super("No se encontró el ejemplar con ID: " + copyId);
    }
}