package com.biblioteca.reservation_service.exception;

public class CopyNotAvailableException extends RuntimeException {
    public CopyNotAvailableException(Long id) {
        super("La copia no esta disponible para reserva. copyId=" + id);
    }
}
