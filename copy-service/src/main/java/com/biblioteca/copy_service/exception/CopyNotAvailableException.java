package com.biblioteca.copy_service.exception;

public class CopyNotAvailableException extends RuntimeException {
    public CopyNotAvailableException(Long id) {
        super("La copia no esta disponible. copyId=" + id);
    }
}
