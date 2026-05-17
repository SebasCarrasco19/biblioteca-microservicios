package com.example.category_service.ControllerCategory;

import com.example.category_service.DtoCategory.DtoCategory;
import com.example.category_service.ModelCategory.ModelCategory;
import com.example.category_service.ServiceCategory.ICategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Slf4j
public class ControllerCategory {

    private final ICategoryService serviceCategory;

    @GetMapping
    public ResponseEntity<List<ModelCategory>> getAllCategories() {
        log.info("GET /api/categories");
        return ResponseEntity.ok(serviceCategory.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModelCategory> getCategoryById(@PathVariable Long id) {
        log.info("GET /api/categories/{}", id);
        return ResponseEntity.ok(serviceCategory.findById(id));
    }

    @PostMapping
    public ResponseEntity<ModelCategory> createCategory(
            @Valid @RequestBody DtoCategory dtoCategory) {

        log.info("POST /api/categories");
        ModelCategory category = serviceCategory.save(dtoCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModelCategory> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody DtoCategory dtoCategory) {

        log.info("PUT /api/categories/{}", id);
        return ResponseEntity.ok(serviceCategory.update(id, dtoCategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        log.info("DELETE /api/categories/{}", id);

        serviceCategory.delete(id);

        return ResponseEntity.ok("Categoría desactivada correctamente con ID: " + id);
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<ModelCategory> activateCategory(@PathVariable Long id) {
        log.info("PATCH /api/categories/{}/activate", id);

        ModelCategory category = serviceCategory.activate(id);

        return ResponseEntity.ok(category);
    }
}