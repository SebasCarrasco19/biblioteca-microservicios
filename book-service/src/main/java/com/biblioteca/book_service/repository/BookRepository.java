package com.biblioteca.book_service.repository;

import com.biblioteca.book_service.model.Book;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByIsbnAndActiveTrue(String isbn);

    boolean existsByIsbn(String isbn);
}
