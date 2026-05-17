package com.example.fine_service.ExceptionFine;

public class RemoteLoanNotFoundException extends RuntimeException {

    public RemoteLoanNotFoundException(Long loanId) {
        super("No se encontró el préstamo con ID: " + loanId);
    }
}