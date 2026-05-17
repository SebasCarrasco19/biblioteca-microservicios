package com.biblioteca.loan_service.exception;

public class LoanStateException extends RuntimeException {

    public LoanStateException(String message) {
        super(message);
    }
}