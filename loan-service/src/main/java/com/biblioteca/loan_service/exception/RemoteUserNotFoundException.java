package com.biblioteca.loan_service.exception;

public class RemoteUserNotFoundException extends RuntimeException {

    public RemoteUserNotFoundException(Long userId) {
        super("No se encontró el usuario con ID: " + userId);
    }
}