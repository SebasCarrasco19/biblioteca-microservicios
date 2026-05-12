package com.biblioteca.book_service.repository;

import com.biblioteca.book_service.model.Book;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByIsbn(String isbn);
    boolean existsByIsbn(String isbn);
    boolean existsByIsbnAndIdNot(String isbn, Long id);
    boolean existsByIdAndStatus(Long id, com.biblioteca.book_service.model.BookStatus status);
}
