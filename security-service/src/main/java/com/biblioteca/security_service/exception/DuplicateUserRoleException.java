package com.biblioteca.security_service.exception;

public class DuplicateUserRoleException extends RuntimeException {

    public DuplicateUserRoleException(String message) {
        super(message);
    }
}