package com.biblioteca.book_service.repository;

import com.biblioteca.book_service.model.Book;
import com.biblioteca.book_service.model.BookStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByIsbn(String isbn);

    boolean existsByIsbn(String isbn);

    boolean existsByIsbnAndIdNot(String isbn, Long id);

    boolean existsByIdAndStatus(Long id, BookStatus status);
}