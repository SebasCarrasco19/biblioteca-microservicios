package com.biblioteca.copy_service.controller;

import com.biblioteca.copy_service.dto.AvailabilityResponse;
import com.biblioteca.copy_service.dto.CopyRequest;
import com.biblioteca.copy_service.dto.CopyResponse;
import com.biblioteca.copy_service.dto.MessageResponse;
import com.biblioteca.copy_service.service.CopyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/copies")
@RequiredArgsConstructor
@Tag(name = "Copias", description = "Operaciones para administrar ejemplares físicos y su disponibilidad")
public class CopyController {

    private final CopyService service;

    @PostMapping
    @Operation(summary = "Crear copia", description = "Registra un nuevo ejemplar físico asociado a un libro.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Copia creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de la copia inválidos"),
            @ApiResponse(responseCode = "404", description = "Libro asociado no encontrado"),
            @ApiResponse(responseCode = "409", description = "Código de inventario ya registrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<CopyResponse> createCopy(@Valid @RequestBody CopyRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createCopy(request));
    }

    @GetMapping
    @Operation(summary = "Listar copias", description = "Retorna todos los ejemplares físicos registrados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de copias obtenido correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<CopyResponse>> getCopies() {
        return ResponseEntity.ok(service.getCopies());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar copia por ID", description = "Obtiene la información de una copia mediante su identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Copia encontrada"),
            @ApiResponse(responseCode = "404", description = "Copia no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<CopyResponse> getCopyById(
            @Parameter(description = "Identificador de la copia", example = "1", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(service.getCopyById(id));
    }

    @GetMapping("/{id}/available")
    @Operation(summary = "Consultar disponibilidad", description = "Indica si una copia se encuentra disponible para préstamo o reserva.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Disponibilidad consultada correctamente"),
            @ApiResponse(responseCode = "404", description = "Copia no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<AvailabilityResponse> available(
            @Parameter(description = "Identificador de la copia", example = "1", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(new AvailabilityResponse(service.isAvailable(id)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar copia", description = "Actualiza los datos de un ejemplar físico existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Copia actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de la copia inválidos"),
            @ApiResponse(responseCode = "404", description = "Copia o libro no encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflicto con código de inventario existente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<CopyResponse> updateCopy(
            @Parameter(description = "Identificador de la copia", example = "1", required = true)
            @PathVariable Long id,
            @Valid @RequestBody CopyRequest request) {
        return ResponseEntity.ok(service.updateCopy(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Desactivar copia", description = "Desactiva lógicamente un ejemplar físico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Copia desactivada correctamente"),
            @ApiResponse(responseCode = "404", description = "Copia no encontrada"),
            @ApiResponse(responseCode = "409", description = "La copia no puede desactivarse por su estado actual"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<MessageResponse> deactivateCopy(
            @Parameter(description = "Identificador de la copia", example = "1", required = true)
            @PathVariable Long id) {
        service.deactivateCopy(id);
        return ResponseEntity.ok(MessageResponse.builder()
                .message("Copy deactivated successfully. ID: " + id)
                .status(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build());
    }

    @PatchMapping("/{id}/activate")
    @Operation(summary = "Activar copia", description = "Reactiva una copia previamente desactivada.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Copia activada correctamente"),
            @ApiResponse(responseCode = "404", description = "Copia no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<CopyResponse> activateCopy(
            @Parameter(description = "Identificador de la copia", example = "1", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(service.activateCopy(id));
    }

    @PatchMapping("/{id}/reserve")
    @Operation(summary = "Reservar copia", description = "Cambia el estado de una copia a reservada.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Copia reservada correctamente"),
            @ApiResponse(responseCode = "404", description = "Copia no encontrada"),
            @ApiResponse(responseCode = "409", description = "La copia no está disponible para reserva"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<CopyResponse> reserveCopy(
            @Parameter(description = "Identificador de la copia", example = "1", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(service.reserveCopy(id));
    }

    @PatchMapping("/{id}/release")
    @Operation(summary = "Liberar copia", description = "Libera una copia reservada para que vuelva a estar disponible.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Copia liberada correctamente"),
            @ApiResponse(responseCode = "404", description = "Copia no encontrada"),
            @ApiResponse(responseCode = "409", description = "La copia no se encuentra reservada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<CopyResponse> releaseCopy(
            @Parameter(description = "Identificador de la copia", example = "1", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(service.releaseCopy(id));
    }

    @PatchMapping("/{id}/borrow")
    @Operation(summary = "Marcar copia como prestada", description = "Cambia el estado de una copia a prestada.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Copia marcada como prestada"),
            @ApiResponse(responseCode = "404", description = "Copia no encontrada"),
            @ApiResponse(responseCode = "409", description = "La copia no está disponible para préstamo"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<CopyResponse> borrowCopy(
            @Parameter(description = "Identificador de la copia", example = "1", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(service.borrowCopy(id));
    }

    @PatchMapping("/{id}/return")
    @Operation(summary = "Registrar devolución de copia", description = "Cambia el estado de una copia prestada nuevamente a disponible.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Devolución de la copia registrada correctamente"),
            @ApiResponse(responseCode = "404", description = "Copia no encontrada"),
            @ApiResponse(responseCode = "409", description = "La copia no se encuentra prestada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<CopyResponse> returnCopy(
            @Parameter(description = "Identificador de la copia", example = "1", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(service.returnCopy(id));
    }

    @PostMapping("/{id}/reserve")
    @Operation(summary = "Reservar copia mediante POST", description = "Endpoint alternativo utilizado por otros microservicios para reservar una copia.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Copia reservada correctamente"),
            @ApiResponse(responseCode = "404", description = "Copia no encontrada"),
            @ApiResponse(responseCode = "409", description = "La copia no está disponible para reserva"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<CopyResponse> reserveCopyPost(
            @Parameter(description = "Identificador de la copia", example = "1", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(service.reserveCopy(id));
    }

    @PostMapping("/{id}/release")
    @Operation(summary = "Liberar copia mediante POST", description = "Endpoint alternativo utilizado por otros microservicios para liberar una copia reservada.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Copia liberada correctamente"),
            @ApiResponse(responseCode = "404", description = "Copia no encontrada"),
            @ApiResponse(responseCode = "409", description = "La copia no se encuentra reservada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<CopyResponse> releaseCopyPost(
            @Parameter(description = "Identificador de la copia", example = "1", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(service.releaseCopy(id));
    }
}
