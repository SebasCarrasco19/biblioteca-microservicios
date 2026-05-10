package com.biblioteca.security_service.controller;

import com.biblioteca.security_service.dto.UserRoleRequest;
import com.biblioteca.security_service.dto.UserRoleResponse;
import com.biblioteca.security_service.service.UserRoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.biblioteca.security_service.dto.MessageResponse;
import java.time.LocalDateTime;

import java.util.List;

@RestController
@RequestMapping("/api/user-roles")
public class UserRoleController {

    @Autowired
    private UserRoleService service;

    @PostMapping
    public ResponseEntity<UserRoleResponse> asignarRolAUsuario(@Valid @RequestBody UserRoleRequest request) {
        UserRoleResponse response = service.asignarRolAUsuario(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserRoleResponse>> listarRolesPorUsuario(@PathVariable Long userId) {
        return ResponseEntity.ok(service.listarRolesPorUsuario(userId));
    }

    @GetMapping("/validate/{userId}/{roleName}")
    public ResponseEntity<Boolean> validarRolUsuario(
            @PathVariable Long userId,
            @PathVariable String roleName) {

        boolean resultado = service.validarRolUsuario(userId, roleName);
        return ResponseEntity.ok(resultado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> desactivarAsignacion(@PathVariable Long id) {
        service.desactivarAsignacion(id);

        MessageResponse response = MessageResponse.builder()
                .message("Asignación de rol desactivada correctamente con ID: " + id)
                .status(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.ok(response);
    }
}