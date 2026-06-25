package com.biblioteca.notification_service.controller;

import com.biblioteca.notification_service.dto.MessageResponse;
import com.biblioteca.notification_service.dto.NotificationRequest;
import com.biblioteca.notification_service.dto.NotificationResponse;
import com.biblioteca.notification_service.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Tag(name = "Notificaciones", description = "Operaciones para crear, consultar, enviar, leer y cancelar notificaciones")
public class NotificationController {

    private final NotificationService service;

    @PostMapping
    @Operation(summary = "Crear notificación", description = "Registra una nueva notificación para un usuario.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Notificación creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de la notificación inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public NotificationResponse create(@Valid @RequestBody NotificationRequest request) {
        return service.create(request);
    }

    @GetMapping
    @Operation(summary = "Listar notificaciones", description = "Retorna todas las notificaciones registradas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de notificaciones obtenido correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public List<NotificationResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar notificación por ID", description = "Obtiene una notificación mediante su identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Notificación encontrada"),
            @ApiResponse(responseCode = "404", description = "Notificación no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public NotificationResponse getById(
            @Parameter(description = "Identificador de la notificación", example = "1", required = true)
            @PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Listar notificaciones por usuario", description = "Retorna las notificaciones asociadas a un usuario.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Notificaciones del usuario obtenidas correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public List<NotificationResponse> getByUserId(
            @Parameter(description = "Identificador del usuario", example = "1", required = true)
            @PathVariable Long userId) {
        return service.getByUserId(userId);
    }

    @PatchMapping("/{id}/send")
    @Operation(summary = "Enviar notificación", description = "Marca una notificación pendiente como enviada.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Notificación enviada correctamente"),
            @ApiResponse(responseCode = "404", description = "Notificación no encontrada"),
            @ApiResponse(responseCode = "409", description = "La notificación no puede enviarse por su estado actual"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public NotificationResponse send(
            @Parameter(description = "Identificador de la notificación", example = "1", required = true)
            @PathVariable Long id) {
        return service.send(id);
    }

    @PatchMapping("/{id}/read")
    @Operation(summary = "Marcar notificación como leída", description = "Marca una notificación enviada como leída.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Notificación marcada como leída"),
            @ApiResponse(responseCode = "404", description = "Notificación no encontrada"),
            @ApiResponse(responseCode = "409", description = "La notificación no puede marcarse como leída por su estado actual"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public NotificationResponse read(
            @Parameter(description = "Identificador de la notificación", example = "1", required = true)
            @PathVariable Long id) {
        return service.read(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancelar notificación", description = "Cancela una notificación pendiente o enviada según las reglas de negocio.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Notificación cancelada correctamente"),
            @ApiResponse(responseCode = "404", description = "Notificación no encontrada"),
            @ApiResponse(responseCode = "409", description = "La notificación no puede cancelarse por su estado actual"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public MessageResponse cancel(
            @Parameter(description = "Identificador de la notificación", example = "1", required = true)
            @PathVariable Long id) {
        return service.cancel(id);
    }
}
