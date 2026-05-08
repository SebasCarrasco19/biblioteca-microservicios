package com.example.category_service.ControllerCategory;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.category_service.DtoCategory.DtoCategory;
import com.example.category_service.ModelCategory.ModelCategory;
import com.example.category_service.ServiceCategory.ICategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/categories")

@RequiredArgsConstructor
@Slf4j

public class ControllerCategory {

    private final ICategoryService serviceCategory;

    @GetMapping
    public ResponseEntity<List<ModelCategory>> getAllCategories() {

        log.info("GET /categories");

        return ResponseEntity.ok(serviceCategory.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModelCategory> getCategoryById(@PathVariable Long id) {

        log.info("GET /categories/{}", id);

        return ResponseEntity.ok(serviceCategory.findById(id));
    }

    @PostMapping
    public ResponseEntity<ModelCategory> createCategory(
            @Valid @RequestBody DtoCategory dtoCategory) {

        log.info("POST /categories");

        return ResponseEntity.ok(serviceCategory.save(dtoCategory));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModelCategory> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody DtoCategory dtoCategory) {

        log.info("PUT /categories/{}", id);

        return ResponseEntity.ok(serviceCategory.update(id, dtoCategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {

        log.info("DELETE /categories/{}", id);

        serviceCategory.delete(id);

        return ResponseEntity.ok("Categoría eliminada correctamente");
    }
}