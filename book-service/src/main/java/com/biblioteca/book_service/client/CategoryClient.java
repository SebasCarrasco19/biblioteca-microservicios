package com.biblioteca.book_service.client;

import com.biblioteca.book_service.dto.CategoryClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "category-service")
public interface CategoryClient {

    @GetMapping("/api/categories/{id}")
    CategoryClientResponse findById(@PathVariable("id") Long id);
}
