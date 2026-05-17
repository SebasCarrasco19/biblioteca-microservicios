package com.biblioteca.book_service.service;

import com.biblioteca.book_service.dto.BookRequest;
import com.biblioteca.book_service.dto.BookResponse;

import java.util.List;

public interface BookService {

    BookResponse createBook(BookRequest request, Long userId);

    List<BookResponse> getBooks();

    BookResponse getBookById(Long id);

    boolean existsById(Long id);

    BookResponse updateBook(Long id, BookRequest request, Long userId);

    void deactivateBook(Long id, Long userId);

    BookResponse activateBook(Long id, Long userId);
}
