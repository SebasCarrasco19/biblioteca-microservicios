package com.biblioteca.book_service.exception;

public class ForbiddenActionException extends RuntimeException {

    public ForbiddenActionException(String action) {
        super("No tiene permisos para realizar la acción: " + action);
    }
}
