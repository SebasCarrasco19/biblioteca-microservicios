package com.biblioteca.book_service.controller;

import com.biblioteca.book_service.dto.BookRequest;
import com.biblioteca.book_service.dto.BookResponse;
import com.biblioteca.book_service.dto.MessageResponse;
import com.biblioteca.book_service.service.BookService;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Libros", description = "Operaciones para administrar el catálogo de libros")
public class BookController {

    @Autowired
    private BookService service;

    @PostMapping
    @Operation(summary = "Crear libro", description = "Registra un nuevo libro en el catálogo. Requiere el identificador del usuario responsable.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Libro creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos del libro inválidos"),
            @ApiResponse(responseCode = "401", description = "Usuario no autenticado"),
            @ApiResponse(responseCode = "403", description = "Usuario sin permisos para crear libros"),
            @ApiResponse(responseCode = "404", description = "Categoría o usuario no encontrado"),
            @ApiResponse(responseCode = "409", description = "El libro ya se encuentra registrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<BookResponse> createBook(
            @Valid @RequestBody BookRequest request,
            @Parameter(description = "Identificador del usuario que ejecuta la operación", example = "1", required = true)
            @RequestHeader("X-User-Id") Long userId) {

        BookResponse response = service.createBook(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar libros", description = "Retorna todos los libros disponibles en el catálogo.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de libros obtenido correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<BookResponse>> getBooks() {
        return ResponseEntity.ok(service.getBooks());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar libro por ID", description = "Obtiene la información de un libro mediante su identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Libro encontrado"),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<BookResponse> getBookById(
            @Parameter(description = "Identificador del libro", example = "1", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(service.getBookById(id));
    }

    @GetMapping("/{id}/exists")
    @Operation(summary = "Verificar existencia de libro", description = "Indica si existe un libro con el identificador informado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Verificación realizada correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Map<String, Boolean>> exists(
            @Parameter(description = "Identificador del libro", example = "1", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(Map.of("exists", service.existsById(id)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar libro", description = "Actualiza los datos de un libro existente. Requiere un usuario autorizado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Libro actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos del libro inválidos"),
            @ApiResponse(responseCode = "401", description = "Usuario no autenticado"),
            @ApiResponse(responseCode = "403", description = "Usuario sin permisos para actualizar libros"),
            @ApiResponse(responseCode = "404", description = "Libro, categoría o usuario no encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflicto con otro libro existente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<BookResponse> updateBook(
            @Parameter(description = "Identificador del libro", example = "1", required = true)
            @PathVariable Long id,
            @Valid @RequestBody BookRequest request,
            @Parameter(description = "Identificador del usuario que ejecuta la operación", example = "1", required = true)
            @RequestHeader("X-User-Id") Long userId) {

        BookResponse response = service.updateBook(id, request, userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Desactivar libro", description = "Desactiva lógicamente un libro del catálogo. Requiere un usuario autorizado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Libro desactivado correctamente"),
            @ApiResponse(responseCode = "401", description = "Usuario no autenticado"),
            @ApiResponse(responseCode = "403", description = "Usuario sin permisos para desactivar libros"),
            @ApiResponse(responseCode = "404", description = "Libro o usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<MessageResponse> deactivateBook(
            @Parameter(description = "Identificador del libro", example = "1", required = true)
            @PathVariable Long id,
            @Parameter(description = "Identificador del usuario que ejecuta la operación", example = "1", required = true)
            @RequestHeader("X-User-Id") Long userId) {

        service.deactivateBook(id, userId);

        MessageResponse response = MessageResponse.builder()
                .message("Libro desactivado correctamente con ID: " + id)
                .status(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/activate")
    @Operation(summary = "Activar libro", description = "Reactiva un libro previamente desactivado. Requiere un usuario autorizado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Libro activado correctamente"),
            @ApiResponse(responseCode = "401", description = "Usuario no autenticado"),
            @ApiResponse(responseCode = "403", description = "Usuario sin permisos para activar libros"),
            @ApiResponse(responseCode = "404", description = "Libro o usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<BookResponse> activateBook(
            @Parameter(description = "Identificador del libro", example = "1", required = true)
            @PathVariable Long id,
            @Parameter(description = "Identificador del usuario que ejecuta la operación", example = "1", required = true)
            @RequestHeader("X-User-Id") Long userId) {

        BookResponse response = service.activateBook(id, userId);
        return ResponseEntity.ok(response);
    }
}
