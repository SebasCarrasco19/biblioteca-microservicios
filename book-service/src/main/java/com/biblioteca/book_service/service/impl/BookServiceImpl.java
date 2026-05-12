package com.biblioteca.book_service.service.impl;

import com.biblioteca.book_service.client.CategoryClient;
import com.biblioteca.book_service.client.SecurityClient;
import com.biblioteca.book_service.dto.BookRequest;
import com.biblioteca.book_service.dto.BookResponse;
import com.biblioteca.book_service.dto.CategoryClientResponse;
import com.biblioteca.book_service.exception.*;
import com.biblioteca.book_service.model.Book;
import com.biblioteca.book_service.model.BookStatus;
import com.biblioteca.book_service.repository.BookRepository;
import com.biblioteca.book_service.service.BookService;
import feign.FeignException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository repository;
    private final SecurityClient securityClient;
    private final CategoryClient categoryClient;

    @Override
    public BookResponse createBook(BookRequest request, Long userId) {
        validatePermission(userId);
        validateCategory(request.getCategoryId());

        String isbn = normalizeIsbn(request.getIsbn());
        if (repository.existsByIsbn(isbn)) throw new DuplicateIsbnException(isbn);

        Book book = new Book();
        applyRequest(book, request);
        book.setIsbn(isbn);
        book.setStatus(BookStatus.ACTIVO);

        Book saved = repository.save(book);
        log.info("Book created id={} isbn={}", saved.getId(), saved.getIsbn());
        return toResponse(saved);
    }

    @Override
    public List<BookResponse> getBooks() {
        return repository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public BookResponse getBookById(Long id) {
        return toResponse(repository.findById(id).orElseThrow(() -> new BookNotFoundException(id)));
    }

    @Override
    public BookResponse updateBook(Long id, BookRequest request, Long userId) {
        validatePermission(userId);
        validateCategory(request.getCategoryId());

        Book book = repository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        String isbn = normalizeIsbn(request.getIsbn());
        if (repository.existsByIsbnAndIdNot(isbn, id)) throw new DuplicateIsbnException(isbn);

        applyRequest(book, request);
        book.setIsbn(isbn);

        Book updated = repository.save(book);
        log.info("Book updated id={}", updated.getId());
        return toResponse(updated);
    }

    @Override
    public void deactivateBook(Long id, Long userId) {
        validatePermission(userId);
        Book book = repository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        if (BookStatus.INACTIVO.equals(book.getStatus())) {
            throw new BookStateException("Book is already INACTIVO");
        }
        book.setStatus(BookStatus.INACTIVO);
        repository.save(book);
    }

    @Override
    public BookResponse activateBook(Long id, Long userId) {
        validatePermission(userId);
        Book book = repository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        if (BookStatus.ACTIVO.equals(book.getStatus())) {
            throw new BookStateException("Book is already ACTIVO");
        }
        book.setStatus(BookStatus.ACTIVO);
        return toResponse(repository.save(book));
    }

    @Override
    public boolean existsById(Long id) {
        return repository.findById(id).map(b -> BookStatus.ACTIVO.equals(b.getStatus())).orElse(false);
    }

    private void validatePermission(Long userId) {
        try {
            Boolean isAdmin = securityClient.validateRole(userId, "ADMIN");
            Boolean isBibliotecario = securityClient.validateRole(userId, "BIBLIOTECARIO");
            if (!Boolean.TRUE.equals(isAdmin) && !Boolean.TRUE.equals(isBibliotecario)) {
                throw new ForbiddenActionException("User does not have ADMIN or BIBLIOTECARIO role");
            }
        } catch (FeignException ex) {
            throw new RemoteServiceException("Error validating roles in security-service");
        }
    }

    private void validateCategory(Long categoryId) {
        try {
            CategoryClientResponse response = categoryClient.findById(categoryId);
            if (response == null) {
                throw new RemoteCategoryStateException("Invalid category response from category-service");
            }
            String state = response.getEstado() != null ? response.getEstado() : response.getStatus();
            if (state != null && !"ACTIVO".equalsIgnoreCase(state)) {
                throw new RemoteCategoryStateException("Category is not ACTIVO");
            }
        } catch (FeignException.NotFound ex) {
            throw new RemoteCategoryNotFoundException(categoryId);
        } catch (FeignException ex) {
            throw new RemoteServiceException("Error validating category in category-service");
        }
    }

    private void applyRequest(Book book, BookRequest request) {
        book.setTitle(normalizeText(request.getTitle()));
        book.setDescription(normalizeText(request.getDescription()));
        book.setAuthor(normalizeText(request.getAuthor()));
        book.setEditorial(normalizeText(request.getEditorial()));
        book.setCategoryId(request.getCategoryId());
        book.setPublishedYear(request.getPublishedYear());
    }

    private BookResponse toResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .description(book.getDescription())
                .author(book.getAuthor())
                .editorial(book.getEditorial())
                .categoryId(book.getCategoryId())
                .publishedYear(book.getPublishedYear())
                .status(book.getStatus().name())
                .createdAt(book.getCreatedAt())
                .updatedAt(book.getUpdatedAt())
                .build();
    }

    private String normalizeIsbn(String isbn) { return normalizeText(isbn).replace("-", ""); }
    private String normalizeText(String value) { return value == null ? null : value.trim(); }
}
