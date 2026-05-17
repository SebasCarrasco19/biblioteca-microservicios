package com.example.fine_service.ExceptionFine;

public class RemoteServiceException extends RuntimeException {

    public RemoteServiceException(String message) {
        super(message);
    }
}