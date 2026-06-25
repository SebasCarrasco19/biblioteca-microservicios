package com.biblioteca.loan_service.controller;

import com.biblioteca.loan_service.dto.LoanRequest;
import com.biblioteca.loan_service.dto.LoanResponse;
import com.biblioteca.loan_service.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
@Tag(name = "Préstamos", description = "Operaciones para crear, consultar, devolver, cancelar y controlar préstamos")
public class LoanController {

    @Autowired
    private LoanService service;

    @PostMapping
    @Operation(summary = "Crear préstamo", description = "Registra un préstamo para una copia disponible y un usuario válido.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Préstamo creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos del préstamo inválidos"),
            @ApiResponse(responseCode = "401", description = "Usuario no autenticado"),
            @ApiResponse(responseCode = "404", description = "Usuario o copia no encontrada"),
            @ApiResponse(responseCode = "409", description = "La copia no está disponible o ya posee un préstamo activo"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<LoanResponse> createLoan(
            @Valid @RequestBody LoanRequest request,
            @Parameter(description = "Identificador del usuario que ejecuta la operación", example = "1", required = true)
            @RequestHeader("X-User-Id") Long userId) {

        LoanResponse response = service.createLoan(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar préstamos", description = "Retorna todos los préstamos registrados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de préstamos obtenido correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<LoanResponse>> getLoans() {
        return ResponseEntity.ok(service.getLoans());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar préstamo por ID", description = "Obtiene un préstamo mediante su identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Préstamo encontrado"),
            @ApiResponse(responseCode = "404", description = "Préstamo no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<LoanResponse> getLoanById(
            @Parameter(description = "Identificador del préstamo", example = "1", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(service.getLoanById(id));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Listar préstamos por usuario", description = "Retorna los préstamos asociados a un usuario.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Préstamos del usuario obtenidos correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<LoanResponse>> getLoansByUser(
            @Parameter(description = "Identificador del usuario", example = "1", required = true)
            @PathVariable Long userId) {
        return ResponseEntity.ok(service.getLoansByUser(userId));
    }

    @PatchMapping("/{id}/return")
    @Operation(summary = "Devolver préstamo", description = "Registra la devolución de un préstamo y libera la copia asociada.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Préstamo devuelto correctamente"),
            @ApiResponse(responseCode = "401", description = "Usuario no autenticado"),
            @ApiResponse(responseCode = "404", description = "Préstamo, usuario o copia no encontrada"),
            @ApiResponse(responseCode = "409", description = "El préstamo no se encuentra en un estado que permita devolución"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<LoanResponse> returnLoan(
            @Parameter(description = "Identificador del préstamo", example = "1", required = true)
            @PathVariable Long id,
            @Parameter(description = "Identificador del usuario que ejecuta la operación", example = "1", required = true)
            @RequestHeader("X-User-Id") Long userId) {

        LoanResponse response = service.returnLoan(id, userId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/cancel")
    @Operation(summary = "Cancelar préstamo", description = "Cancela un préstamo activo y actualiza el estado de la copia asociada.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Préstamo cancelado correctamente"),
            @ApiResponse(responseCode = "401", description = "Usuario no autenticado"),
            @ApiResponse(responseCode = "404", description = "Préstamo, usuario o copia no encontrada"),
            @ApiResponse(responseCode = "409", description = "El préstamo no puede cancelarse por su estado actual"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<LoanResponse> cancelLoan(
            @Parameter(description = "Identificador del préstamo", example = "1", required = true)
            @PathVariable Long id,
            @Parameter(description = "Identificador del usuario que ejecuta la operación", example = "1", required = true)
            @RequestHeader("X-User-Id") Long userId) {

        LoanResponse response = service.cancelLoan(id, userId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/overdue")
    @Operation(summary = "Marcar préstamo atrasado", description = "Cambia el estado de un préstamo vencido a atrasado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Préstamo marcado como atrasado"),
            @ApiResponse(responseCode = "404", description = "Préstamo no encontrado"),
            @ApiResponse(responseCode = "409", description = "El préstamo no puede marcarse como atrasado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<LoanResponse> markLoanAsOverdue(
            @Parameter(description = "Identificador del préstamo", example = "1", required = true)
            @PathVariable Long id) {
        LoanResponse response = service.markLoanAsOverdue(id);
        return ResponseEntity.ok(response);
    }
}
