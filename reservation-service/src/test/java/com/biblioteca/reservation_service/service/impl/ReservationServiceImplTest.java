package com.biblioteca.reservation_service.service.impl;

import com.biblioteca.reservation_service.client.CopyClient;
import com.biblioteca.reservation_service.client.UserClient;
import com.biblioteca.reservation_service.dto.ReservationResponse;
import com.biblioteca.reservation_service.model.Reservation;
import com.biblioteca.reservation_service.model.ReservationStatus;
import com.biblioteca.reservation_service.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceImplTest {
    @Mock private ReservationRepository repository;
    @Mock private CopyClient copyClient;
    @Mock private UserClient userClient;

    @Test
    void buscarPorId_debeRetornarReserva() {
        ReservationServiceImpl service = new ReservationServiceImpl(repository, copyClient, userClient);
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setUserId(10L);
        reservation.setCopyId(20L);
        reservation.setStatus(ReservationStatus.ACTIVA);
        reservation.setActive(true);
        reservation.setReservationDate(LocalDateTime.now());
        reservation.setExpirationDate(LocalDateTime.now().plusDays(1));
        when(repository.findById(1L)).thenReturn(Optional.of(reservation));
        ReservationResponse response = service.buscarPorId(1L);
        assertEquals("ACTIVA", response.getStatus());
    }
}
