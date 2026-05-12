package com.biblioteca.book_service.controller;

import com.biblioteca.book_service.dto.BookRequest;
import com.biblioteca.book_service.dto.BookResponse;
import com.biblioteca.book_service.dto.MessageResponse;
import com.biblioteca.book_service.service.BookService;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService service;

    @PostMapping
    public ResponseEntity<BookResponse> createBook(@Valid @RequestBody BookRequest request,
                                                   @RequestHeader("X-User-Id") Long userId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createBook(request, userId));
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
    public ResponseEntity<BookResponse> updateBook(@PathVariable Long id,
                                                   @Valid @RequestBody BookRequest request,
                                                   @RequestHeader("X-User-Id") Long userId) {
        return ResponseEntity.ok(service.updateBook(id, request, userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deactivateBook(@PathVariable Long id,
                                                          @RequestHeader("X-User-Id") Long userId) {
        service.deactivateBook(id, userId);
        return ResponseEntity.ok(MessageResponse.builder()
                .message("Book deactivated successfully. ID: " + id)
                .status(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build());
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<BookResponse> activateBook(@PathVariable Long id,
                                                     @RequestHeader("X-User-Id") Long userId) {
        return ResponseEntity.ok(service.activateBook(id, userId));
    }
}
