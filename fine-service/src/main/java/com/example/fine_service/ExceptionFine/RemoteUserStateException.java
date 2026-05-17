package com.example.fine_service.ExceptionFine;

public class RemoteUserStateException extends RuntimeException {

    public RemoteUserStateException(String message) {
        super(message);
    }
}