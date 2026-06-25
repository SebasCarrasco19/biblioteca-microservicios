package com.example.fine_service.ControllerFine;

import com.example.fine_service.DtoFine.DtoFine;
import com.example.fine_service.DtoFine.MessageResponse;
import com.example.fine_service.ModelFine.ModelFine;
import com.example.fine_service.ServiceFine.IFineService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/fines")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Multas", description = "Operaciones para crear, consultar, pagar y cancelar multas")
public class ControllerFine {

    private final IFineService serviceFine;

    @GetMapping
    @Operation(summary = "Listar multas", description = "Retorna todas las multas registradas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de multas obtenido correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<ModelFine>> getAllFines() {
        log.info("GET /api/fines");
        return ResponseEntity.ok(serviceFine.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar multa por ID", description = "Obtiene una multa mediante su identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Multa encontrada"),
            @ApiResponse(responseCode = "404", description = "Multa no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ModelFine> getFineById(
            @Parameter(description = "Identificador de la multa", example = "1", required = true)
            @PathVariable Long id) {
        log.info("GET /api/fines/{}", id);
        return ResponseEntity.ok(serviceFine.findById(id));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Listar multas por usuario", description = "Retorna las multas asociadas a un usuario.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Multas del usuario obtenidas correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<ModelFine>> getFinesByUserId(
            @Parameter(description = "Identificador del usuario", example = "1", required = true)
            @PathVariable Long userId) {
        log.info("GET /api/fines/user/{}", userId);
        return ResponseEntity.ok(serviceFine.findByUserId(userId));
    }

    @GetMapping("/loan/{loanId}")
    @Operation(summary = "Listar multas por préstamo", description = "Retorna las multas asociadas a un préstamo.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Multas del préstamo obtenidas correctamente"),
            @ApiResponse(responseCode = "404", description = "Préstamo no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<ModelFine>> getFinesByLoanId(
            @Parameter(description = "Identificador del préstamo", example = "1", required = true)
            @PathVariable Long loanId) {
        log.info("GET /api/fines/loan/{}", loanId);
        return ResponseEntity.ok(serviceFine.findByLoanId(loanId));
    }

    @GetMapping("/pending")
    @Operation(summary = "Listar multas pendientes", description = "Retorna todas las multas que aún no han sido pagadas o canceladas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de multas pendientes obtenido correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<ModelFine>> getPendingFines() {
        log.info("GET /api/fines/pending");
        return ResponseEntity.ok(serviceFine.findPendingFines());
    }

    @PostMapping
    @Operation(summary = "Crear multa", description = "Registra una multa asociada a un usuario y a un préstamo.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Multa creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de la multa inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuario o préstamo no encontrado"),
            @ApiResponse(responseCode = "409", description = "Ya existe una multa equivalente para el préstamo"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ModelFine> createFine(@Valid @RequestBody DtoFine dtoFine) {
        log.info("POST /api/fines");
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceFine.save(dtoFine));
    }

    @PutMapping("/{id}/pay")
    @Operation(summary = "Pagar multa", description = "Marca una multa pendiente como pagada.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Multa pagada correctamente"),
            @ApiResponse(responseCode = "404", description = "Multa no encontrada"),
            @ApiResponse(responseCode = "409", description = "La multa ya fue pagada o cancelada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ModelFine> payFine(
            @Parameter(description = "Identificador de la multa", example = "1", required = true)
            @PathVariable Long id) {
        log.info("PUT /api/fines/{}/pay", id);
        return ResponseEntity.ok(serviceFine.payFine(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancelar multa", description = "Cancela una multa pendiente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Multa cancelada correctamente"),
            @ApiResponse(responseCode = "404", description = "Multa no encontrada"),
            @ApiResponse(responseCode = "409", description = "La multa no puede cancelarse por su estado actual"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<MessageResponse> cancelFine(
            @Parameter(description = "Identificador de la multa", example = "1", required = true)
            @PathVariable Long id) {
        log.info("DELETE /api/fines/{}", id);
        serviceFine.cancelFine(id);

        return ResponseEntity.ok(MessageResponse.builder()
                .message("Multa cancelada correctamente con ID: " + id)
                .status(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build());
    }
}
