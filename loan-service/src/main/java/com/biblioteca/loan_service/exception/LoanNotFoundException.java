package com.biblioteca.loan_service.exception;

public class LoanNotFoundException extends RuntimeException {

    public LoanNotFoundException(Long id) {
        super("No se encontró el préstamo con ID: " + id);
    }
}