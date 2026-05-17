package com.biblioteca.security_service.controller;

import com.biblioteca.security_service.dto.RoleRequest;
import com.biblioteca.security_service.dto.RoleResponse;
import com.biblioteca.security_service.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.biblioteca.security_service.dto.MessageResponse;
import java.time.LocalDateTime;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService service;

    @PostMapping
    public ResponseEntity<RoleResponse> crearRol(@Valid @RequestBody RoleRequest request) {
        RoleResponse response = service.crearRol(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<RoleResponse>> listarRoles() {
        return ResponseEntity.ok(service.listarRoles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> buscarRolPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarRolPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponse> actualizarRol(
            @PathVariable Long id,
            @Valid @RequestBody RoleRequest request) {

        RoleResponse response = service.actualizarRol(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> desactivarRol(@PathVariable Long id) {
        service.desactivarRol(id);

        MessageResponse response = MessageResponse.builder()
                .message("Rol desactivado correctamente con ID: " + id)
                .status(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<RoleResponse> activarRol(@PathVariable Long id) {
        RoleResponse response = service.activarRol(id);
        return ResponseEntity.ok(response);
    }
}