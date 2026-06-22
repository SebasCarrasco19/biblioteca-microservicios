package com.biblioteca.reservation_service.exception;

public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException(Long id) {
        super("No existe la reserva con id: " + id);
    }
}
