package com.biblioteca.security_service.controller;

import com.biblioteca.security_service.dto.MessageResponse;
import com.biblioteca.security_service.dto.RoleRequest;
import com.biblioteca.security_service.dto.RoleResponse;
import com.biblioteca.security_service.service.RoleService;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/roles")
@Tag(name = "Roles", description = "Operaciones para administrar los roles de seguridad del sistema")
public class RoleController {

    @Autowired
    private RoleService service;

    @PostMapping
    @Operation(summary = "Crear rol", description = "Registra un nuevo rol de seguridad.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Rol creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos del rol inválidos"),
            @ApiResponse(responseCode = "409", description = "El rol ya existe"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<RoleResponse> crearRol(@Valid @RequestBody RoleRequest request) {
        RoleResponse response = service.crearRol(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar roles", description = "Retorna todos los roles registrados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de roles obtenido correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<RoleResponse>> listarRoles() {
        return ResponseEntity.ok(service.listarRoles());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar rol por ID", description = "Obtiene un rol específico mediante su identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rol encontrado"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<RoleResponse> buscarRolPorId(
            @Parameter(description = "Identificador del rol", example = "1", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(service.buscarRolPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar rol", description = "Modifica los datos de un rol existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rol actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos del rol inválidos"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflicto con otro rol existente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<RoleResponse> actualizarRol(
            @Parameter(description = "Identificador del rol", example = "1", required = true)
            @PathVariable Long id,
            @Valid @RequestBody RoleRequest request) {

        RoleResponse response = service.actualizarRol(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Desactivar rol", description = "Desactiva lógicamente un rol existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rol desactivado correctamente"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<MessageResponse> desactivarRol(
            @Parameter(description = "Identificador del rol", example = "1", required = true)
            @PathVariable Long id) {
        service.desactivarRol(id);

        MessageResponse response = MessageResponse.builder()
                .message("Rol desactivado correctamente con ID: " + id)
                .status(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/activar")
    @Operation(summary = "Activar rol", description = "Reactiva un rol previamente desactivado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rol activado correctamente"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<RoleResponse> activarRol(
            @Parameter(description = "Identificador del rol", example = "1", required = true)
            @PathVariable Long id) {
        RoleResponse response = service.activarRol(id);
        return ResponseEntity.ok(response);
    }
}
