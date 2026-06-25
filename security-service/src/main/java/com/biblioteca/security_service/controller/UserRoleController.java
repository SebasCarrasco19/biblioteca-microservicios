package com.biblioteca.security_service.controller;

import com.biblioteca.security_service.dto.MessageResponse;
import com.biblioteca.security_service.dto.UserRoleRequest;
import com.biblioteca.security_service.dto.UserRoleResponse;
import com.biblioteca.security_service.service.UserRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/user-roles")
@Tag(name = "Asignación de roles", description = "Operaciones para asignar, consultar, validar y desactivar roles de usuarios")
public class UserRoleController {

    @Autowired
    private UserRoleService service;

    @PostMapping
    @Operation(summary = "Asignar rol a usuario", description = "Crea una asignación entre un usuario y un rol.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Rol asignado correctamente al usuario"),
            @ApiResponse(responseCode = "400", description = "Datos de asignación inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuario o rol no encontrado"),
            @ApiResponse(responseCode = "409", description = "La asignación ya existe"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<UserRoleResponse> asignarRolAUsuario(@Valid @RequestBody UserRoleRequest request) {
        UserRoleResponse response = service.asignarRolAUsuario(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Listar roles de un usuario", description = "Retorna las asignaciones de roles asociadas a un usuario.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Roles del usuario obtenidos correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<UserRoleResponse>> listarRolesPorUsuario(
            @Parameter(description = "Identificador del usuario", example = "1", required = true)
            @PathVariable Long userId) {
        return ResponseEntity.ok(service.listarRolesPorUsuario(userId));
    }

    @GetMapping("/validate/{userId}/{roleName}")
    @Operation(summary = "Validar rol de usuario", description = "Comprueba si un usuario posee un rol específico y activo.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Validación realizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario, rol o asignación no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Boolean> validarRolUsuario(
            @Parameter(description = "Identificador del usuario", example = "1", required = true)
            @PathVariable Long userId,
            @Parameter(description = "Nombre del rol que se desea validar", example = "ADMIN", required = true)
            @PathVariable String roleName) {

        boolean resultado = service.validarRolUsuario(userId, roleName);
        return ResponseEntity.ok(resultado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Desactivar asignación de rol", description = "Desactiva la asociación entre un usuario y un rol.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Asignación desactivada correctamente"),
            @ApiResponse(responseCode = "404", description = "Asignación no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<MessageResponse> desactivarAsignacion(
            @Parameter(description = "Identificador de la asignación", example = "1", required = true)
            @PathVariable Long id) {
        service.desactivarAsignacion(id);

        MessageResponse response = MessageResponse.builder()
                .message("Asignación de rol desactivada correctamente con ID: " + id)
                .status(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.ok(response);
    }
}
