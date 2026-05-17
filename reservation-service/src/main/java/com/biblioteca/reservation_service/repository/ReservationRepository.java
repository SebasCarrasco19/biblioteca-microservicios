package com.biblioteca.reservation_service.repository;

import com.biblioteca.reservation_service.model.Reservation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findByIdAndActiveTrue(Long id);
    List<Reservation> findByUserIdAndActiveTrue(Long userId);
}
