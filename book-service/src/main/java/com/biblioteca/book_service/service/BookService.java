package com.biblioteca.book_service.service;

import com.biblioteca.book_service.client.SecurityClient;
import com.biblioteca.book_service.dto.BookRequest;
import com.biblioteca.book_service.dto.BookResponse;
import com.biblioteca.book_service.dto.RoleValidationResponse;
import com.biblioteca.book_service.exception.BookNotFoundException;
import com.biblioteca.book_service.exception.DuplicateIsbnException;
import com.biblioteca.book_service.exception.ForbiddenActionException;
import com.biblioteca.book_service.exception.RemoteServiceException;
import com.biblioteca.book_service.model.Book;
import com.biblioteca.book_service.repository.BookRepository;
import feign.FeignException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private static final Logger log = LoggerFactory.getLogger(BookService.class);

    private final BookRepository bookRepository;
    private final SecurityClient securityClient;

    public BookResponse create(BookRequest request, Long userId) {
        validatePermission(userId, "CREAR_LIBRO");

        String isbn = normalizeIsbn(request.getIsbn());
        if (bookRepository.existsByIsbn(isbn)) {
            log.warn("Intento de ISBN duplicado: {}", isbn);
            throw new DuplicateIsbnException(isbn);
        }

        Book book = new Book();
        applyRequest(book, request);
        book.setIsbn(isbn);
        book.setActive(Boolean.TRUE);

        Book created = bookRepository.save(book);
        log.info("Libro creado con id={} e isbn={}", created.getId(), created.getIsbn());
        return toResponse(created);
    }

    public List<BookResponse> findAll() {
        return bookRepository.findAll().stream()
                .filter(Book::getActive)
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public BookResponse findById(Long id) {
        Book book = bookRepository.findById(id)
                .filter(Book::getActive)
                .orElseThrow(() -> new BookNotFoundException(id));
        return toResponse(book);
    }

    public BookResponse findByIsbn(String isbn) {
        String normalized = normalizeIsbn(isbn);
        Book book = bookRepository.findByIsbnAndActiveTrue(normalized)
                .orElseThrow(() -> new BookNotFoundException(normalized));
        return toResponse(book);
    }

    public BookResponse update(Long id, BookRequest request, Long userId) {
        validatePermission(userId, "ACTUALIZAR_LIBRO");

        Book book = bookRepository.findById(id)
                .filter(Book::getActive)
                .orElseThrow(() -> new BookNotFoundException(id));

        String newIsbn = normalizeIsbn(request.getIsbn());
        if (!book.getIsbn().equals(newIsbn) && bookRepository.existsByIsbn(newIsbn)) {
            log.warn("Intento de actualizar a ISBN duplicado: {}", newIsbn);
            throw new DuplicateIsbnException(newIsbn);
        }

        applyRequest(book, request);
        book.setIsbn(newIsbn);

        Book updated = bookRepository.save(book);
        log.info("Libro actualizado id={} isbn={}", updated.getId(), updated.getIsbn());
        return toResponse(updated);
    }

    public void delete(Long id, Long userId) {
        validatePermission(userId, "ELIMINAR_LIBRO");

        Book book = bookRepository.findById(id)
                .filter(Book::getActive)
                .orElseThrow(() -> new BookNotFoundException(id));

        book.setActive(Boolean.FALSE);
        bookRepository.save(book);
        log.info("Libro eliminado logicamente id={}", id);
    }

    private void validatePermission(Long userId, String action) {
        try {
            RoleValidationResponse response = securityClient.authorize(userId, action);
            if (response == null || !response.isAllowed()) {
                log.warn("Acceso denegado para userId={} accion={}", userId, action);
                throw new ForbiddenActionException(action);
            }
        } catch (FeignException.Forbidden ex) {
            log.warn("security-service denego la accion={} para userId={}", action, userId);
            throw new ForbiddenActionException(action);
        } catch (FeignException ex) {
            log.error("Error de comunicacion con security-service: status={} accion={} userId={}", ex.status(), action, userId);
            throw new RemoteServiceException("No fue posible validar permisos en security-service");
        } catch (Exception ex) {
            log.error("Fallo inesperado al validar permisos: accion={} userId={} error={}", action, userId, ex.getMessage());
            throw new RemoteServiceException("No fue posible validar permisos en security-service");
        }
    }

    private void applyRequest(Book book, BookRequest request) {
        book.setTitle(normalizeText(request.getTitle()));
        book.setDescription(normalizeText(request.getDescription()));
        book.setAuthor(normalizeText(request.getAuthor()));
        book.setCategory(normalizeText(request.getCategory()));
        book.setPublishedYear(request.getPublishedYear());
    }

    private BookResponse toResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .description(book.getDescription())
                .author(book.getAuthor())
                .category(book.getCategory())
                .publishedYear(book.getPublishedYear())
                .active(book.getActive())
                .createdAt(book.getCreatedAt())
                .updatedAt(book.getUpdatedAt())
                .build();
    }

    private String normalizeIsbn(String isbn) {
        return normalizeText(isbn).replace("-", "");
    }

    private String normalizeText(String value) {
        return value == null ? null : value.trim();
    }
}
