package com.biblioteca.reservation_service.controller;

import com.biblioteca.reservation_service.dto.MessageResponse;
import com.biblioteca.reservation_service.dto.ReservationRequest;
import com.biblioteca.reservation_service.dto.ReservationResponse;
import com.biblioteca.reservation_service.service.ReservationService;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService service;

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@Valid @RequestBody ReservationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearReserva(request));
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> listReservations() {
        return ResponseEntity.ok(service.listarReservas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReservationResponse>> listByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(service.listarPorUsuario(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> cancelReservation(@PathVariable Long id) {
        service.cancelarReserva(id);
        return ResponseEntity.ok(MessageResponse.builder().message("Reservation canceled successfully. ID: " + id).status(HttpStatus.OK.value()).timestamp(LocalDateTime.now()).build());
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<ReservationResponse> activateReservation(@PathVariable Long id) {
        return ResponseEntity.ok(service.activarReserva(id));
    }

    @PatchMapping("/{id}/expirar")
    public ResponseEntity<MessageResponse> expireReservation(@PathVariable Long id) {
        service.expirarReserva(id);
        return ResponseEntity.ok(MessageResponse.builder().message("Reservation expired successfully. ID: " + id).status(HttpStatus.OK.value()).timestamp(LocalDateTime.now()).build());
    }
}
