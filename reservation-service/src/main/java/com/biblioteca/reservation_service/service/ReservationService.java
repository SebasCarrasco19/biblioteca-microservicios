package com.biblioteca.reservation_service.service;

import com.biblioteca.reservation_service.dto.ReservationRequest;
import com.biblioteca.reservation_service.dto.ReservationResponse;
import java.util.List;

public interface ReservationService {
    ReservationResponse crearReserva(ReservationRequest request);
    List<ReservationResponse> listarReservas();
    List<ReservationResponse> listarPorUsuario(Long userId);
    ReservationResponse buscarPorId(Long id);
    void cancelarReserva(Long id);
    ReservationResponse activarReserva(Long id);
    void expirarReserva(Long id);
}
