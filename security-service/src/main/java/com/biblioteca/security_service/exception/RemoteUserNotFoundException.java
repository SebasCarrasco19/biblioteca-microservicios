package com.biblioteca.security_service.exception;

public class RemoteUserNotFoundException extends RuntimeException {

    public RemoteUserNotFoundException(String message) {
        super(message);
    }
}