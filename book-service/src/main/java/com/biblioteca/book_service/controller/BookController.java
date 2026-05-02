package com.biblioteca.book_service.controller;

import com.biblioteca.book_service.dto.BookRequest;
import com.biblioteca.book_service.dto.BookResponse;
import com.biblioteca.book_service.service.BookService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/libros")
@RequiredArgsConstructor
public class BookController {

    private static final String USER_ID_HEADER = "X-Id-Usuario";

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponse> crear(@Valid @RequestBody BookRequest request,
                                              @RequestHeader(USER_ID_HEADER) Long userId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.create(request, userId));
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> listar() {
        return ResponseEntity.ok(bookService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.findById(id));
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookResponse> obtenerPorIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(bookService.findByIsbn(isbn));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> actualizar(@PathVariable Long id,
                                                   @Valid @RequestBody BookRequest request,
                                                   @RequestHeader(USER_ID_HEADER) Long userId) {
        return ResponseEntity.ok(bookService.update(id, request, userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id,
                                         @RequestHeader(USER_ID_HEADER) Long userId) {
        bookService.delete(id, userId);
        return ResponseEntity.noContent().build();
    }
}
