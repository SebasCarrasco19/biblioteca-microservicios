package com.biblioteca.book_service.service.impl;

import com.biblioteca.book_service.client.CategoryClient;
import com.biblioteca.book_service.client.SecurityClient;
import com.biblioteca.book_service.model.Book;
import com.biblioteca.book_service.model.BookStatus;
import com.biblioteca.book_service.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    @Mock private BookRepository repository;
    @Mock private SecurityClient securityClient;
    @Mock private CategoryClient categoryClient;

    @Test
    void existsById_debeRetornarTrueSoloSiLibroEstaActivo() {
        BookServiceImpl service = new BookServiceImpl(repository, securityClient, categoryClient);
        Book book = new Book();
        book.setId(1L);
        book.setStatus(BookStatus.ACTIVO);
        when(repository.findById(1L)).thenReturn(Optional.of(book));
        assertTrue(service.existsById(1L));
    }
}
