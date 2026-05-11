package com.biblioteca.auth_service.exception;

public class CredentialNotFoundException extends RuntimeException {

    public CredentialNotFoundException(String message) {
        super(message);
    }
}