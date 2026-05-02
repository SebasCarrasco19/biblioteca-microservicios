package com.biblioteca.book_service.exception;

public class ForbiddenActionException extends RuntimeException {

    public ForbiddenActionException(String action) {
        super("No tienes permisos para realizar la accion: " + action);
    }
}
