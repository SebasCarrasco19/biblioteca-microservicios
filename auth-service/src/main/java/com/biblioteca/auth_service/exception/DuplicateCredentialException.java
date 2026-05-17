package com.biblioteca.auth_service.exception;

public class DuplicateCredentialException extends RuntimeException {

    public DuplicateCredentialException(String message) {
        super(message);
    }
}