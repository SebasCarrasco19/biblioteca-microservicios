package com.biblioteca.security_service.exception;

public class DuplicateRoleException extends RuntimeException {

    public DuplicateRoleException(String message) {
        super(message);
    }
}
