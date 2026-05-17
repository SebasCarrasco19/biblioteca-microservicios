package com.example.fine_service.ExceptionFine;

public class FineStateException extends RuntimeException {

    public FineStateException(String message) {
        super(message);
    }
}