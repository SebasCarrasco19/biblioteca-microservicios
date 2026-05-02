package com.biblioteca.user_service.controller;

import com.biblioteca.user_service.dto.UserRequest;
import com.biblioteca.user_service.dto.UserResponse;
import com.biblioteca.user_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UserResponse> crearUsuario(@Valid @RequestBody UserRequest request) {
        UserResponse response = service.crearUsuario(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> listarUsuarios() {
        return ResponseEntity.ok(service.listarUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> actualizarUsuario(
            @PathVariable Long id,
            @Valid @RequestBody UserRequest request) {

        UserResponse response = service.actualizarUsuario(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivarUsuario(@PathVariable Long id) {
        service.desactivarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
