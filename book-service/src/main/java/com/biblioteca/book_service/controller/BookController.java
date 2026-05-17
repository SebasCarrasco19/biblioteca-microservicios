package com.biblioteca.book_service.controller;

import com.biblioteca.book_service.dto.BookRequest;
import com.biblioteca.book_service.dto.BookResponse;
import com.biblioteca.book_service.dto.MessageResponse;
import com.biblioteca.book_service.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService service;

    @PostMapping
    public ResponseEntity<BookResponse> createBook(
            @Valid @RequestBody BookRequest request,
            @RequestHeader("X-User-Id") Long userId) {

        BookResponse response = service.createBook(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> getBooks() {
        return ResponseEntity.ok(service.getBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getBookById(id));
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Map<String, Boolean>> exists(@PathVariable Long id) {
        return ResponseEntity.ok(Map.of("exists", service.existsById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody BookRequest request,
            @RequestHeader("X-User-Id") Long userId) {

        BookResponse response = service.updateBook(id, request, userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deactivateBook(
            @PathVariable Long id,
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
    public ResponseEntity<BookResponse> activateBook(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId) {

        BookResponse response = service.activateBook(id, userId);
        return ResponseEntity.ok(response);
    }
}
