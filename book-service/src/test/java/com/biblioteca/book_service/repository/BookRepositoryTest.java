package com.biblioteca.book_service.repository;

import com.biblioteca.book_service.model.Book;
import com.biblioteca.book_service.model.BookStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookRepositoryTest {
    @Autowired private BookRepository repository;

    @Test
    void existsByIsbn_debeEncontrarLibro() {
        Book book = new Book();
        book.setTitle("El Principito");
        book.setIsbn("9781234567890");
        book.setDescription("Clásico");
        book.setAuthor("Antoine de Saint-Exupéry");
        book.setEditorial("Editorial");
        book.setCategoryId(1L);
        book.setPublishedYear(1943);
        book.setStatus(BookStatus.ACTIVO);
        repository.save(book);
        assertTrue(repository.existsByIsbn("9781234567890"));
    }
}
