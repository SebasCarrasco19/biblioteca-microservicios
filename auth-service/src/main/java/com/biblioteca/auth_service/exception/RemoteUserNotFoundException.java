package com.biblioteca.auth_service.exception;

public class RemoteUserNotFoundException extends RuntimeException {

    public RemoteUserNotFoundException(String message) {
        super(message);
    }
}