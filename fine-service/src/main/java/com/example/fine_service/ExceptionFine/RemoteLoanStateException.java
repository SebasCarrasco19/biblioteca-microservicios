package com.example.fine_service.ExceptionFine;

public class RemoteLoanStateException extends RuntimeException {

    public RemoteLoanStateException(String message) {
        super(message);
    }
}