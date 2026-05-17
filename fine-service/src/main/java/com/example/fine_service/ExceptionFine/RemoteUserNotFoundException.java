package com.example.fine_service.ExceptionFine;

public class RemoteUserNotFoundException extends RuntimeException {

    public RemoteUserNotFoundException(Long userId) {
        super("No se encontró el usuario con ID: " + userId);
    }
}