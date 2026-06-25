package com.biblioteca.reservation_service.controller;

import com.biblioteca.reservation_service.dto.MessageResponse;
import com.biblioteca.reservation_service.dto.ReservationRequest;
import com.biblioteca.reservation_service.dto.ReservationResponse;
import com.biblioteca.reservation_service.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
@Tag(name = "Reservas", description = "Operaciones para crear, consultar, cancelar, activar y expirar reservas")
public class ReservationController {

    private final ReservationService service;

    @PostMapping
    @Operation(summary = "Crear reserva", description = "Registra una reserva para un usuario y una copia disponible.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Reserva creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de la reserva inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuario o copia no encontrada"),
            @ApiResponse(responseCode = "409", description = "La copia no está disponible o ya existe una reserva activa"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ReservationResponse> createReservation(@Valid @RequestBody ReservationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearReserva(request));
    }

    @GetMapping
    @Operation(summary = "Listar reservas", description = "Retorna todas las reservas registradas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de reservas obtenido correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<ReservationResponse>> listReservations() {
        return ResponseEntity.ok(service.listarReservas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar reserva por ID", description = "Obtiene una reserva mediante su identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reserva encontrada"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ReservationResponse> getById(
            @Parameter(description = "Identificador de la reserva", example = "1", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Listar reservas por usuario", description = "Retorna las reservas asociadas a un usuario.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reservas del usuario obtenidas correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<ReservationResponse>> listByUser(
            @Parameter(description = "Identificador del usuario", example = "1", required = true)
            @PathVariable Long userId) {
        return ResponseEntity.ok(service.listarPorUsuario(userId));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancelar reserva", description = "Cancela una reserva activa y libera la copia asociada.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reserva cancelada correctamente"),
            @ApiResponse(responseCode = "404", description = "Reserva o copia no encontrada"),
            @ApiResponse(responseCode = "409", description = "La reserva no puede cancelarse por su estado actual"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<MessageResponse> cancelReservation(
            @Parameter(description = "Identificador de la reserva", example = "1", required = true)
            @PathVariable Long id) {
        service.cancelarReserva(id);
        return ResponseEntity.ok(MessageResponse.builder()
                .message("Reservation canceled successfully. ID: " + id)
                .status(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build());
    }

    @PatchMapping("/{id}/activar")
    @Operation(summary = "Activar reserva", description = "Reactiva una reserva previamente cancelada o inactiva cuando la regla de negocio lo permite.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reserva activada correctamente"),
            @ApiResponse(responseCode = "404", description = "Reserva o copia no encontrada"),
            @ApiResponse(responseCode = "409", description = "La reserva no puede activarse por su estado actual"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ReservationResponse> activateReservation(
            @Parameter(description = "Identificador de la reserva", example = "1", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(service.activarReserva(id));
    }

    @PatchMapping("/{id}/expirar")
    @Operation(summary = "Expirar reserva", description = "Marca una reserva vencida como expirada y libera la copia asociada.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reserva expirada correctamente"),
            @ApiResponse(responseCode = "404", description = "Reserva o copia no encontrada"),
            @ApiResponse(responseCode = "409", description = "La reserva no puede expirar por su estado actual"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<MessageResponse> expireReservation(
            @Parameter(description = "Identificador de la reserva", example = "1", required = true)
            @PathVariable Long id) {
        service.expirarReserva(id);
        return ResponseEntity.ok(MessageResponse.builder()
                .message("Reservation expired successfully. ID: " + id)
                .status(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build());
    }
}
