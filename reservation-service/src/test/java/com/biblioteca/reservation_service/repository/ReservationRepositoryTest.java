package com.biblioteca.reservation_service.repository;

import com.biblioteca.reservation_service.model.Reservation;
import com.biblioteca.reservation_service.model.ReservationStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ReservationRepositoryTest {
    @Autowired private ReservationRepository repository;

    @Test
    void findByIdAndActiveTrue_debeEncontrarReservaActiva() {
        Reservation reservation = new Reservation();
        reservation.setUserId(1L);
        reservation.setCopyId(2L);
        reservation.setStatus(ReservationStatus.ACTIVA);
        reservation.setActive(true);
        reservation.setReservationDate(LocalDateTime.now());
        reservation.setExpirationDate(LocalDateTime.now().plusDays(1));
        Reservation saved = repository.save(reservation);
        assertTrue(repository.findByIdAndActiveTrue(saved.getId()).isPresent());
    }
}
