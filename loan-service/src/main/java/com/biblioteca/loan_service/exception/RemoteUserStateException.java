package com.biblioteca.loan_service.exception;

public class RemoteUserStateException extends RuntimeException {

    public RemoteUserStateException(String message) {
        super(message);
    }
}