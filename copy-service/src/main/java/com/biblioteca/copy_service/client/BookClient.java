package com.biblioteca.copy_service.client;

import com.biblioteca.copy_service.dto.BookClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "book-service")
public interface BookClient {
    @GetMapping("/api/books/{id}")
    BookClientResponse getBookById(@PathVariable("id") Long id);
}
