package com.example.category_service.ControllerCategory;

import com.example.category_service.DtoCategory.DtoCategory;
import com.example.category_service.ModelCategory.ModelCategory;
import com.example.category_service.ServiceCategory.ICategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Categorías", description = "Operaciones para administrar las categorías bibliográficas")
public class ControllerCategory {

    private final ICategoryService serviceCategory;

    @GetMapping
    @Operation(summary = "Listar categorías", description = "Retorna todas las categorías bibliográficas registradas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de categorías obtenido correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<ModelCategory>> getAllCategories() {
        log.info("GET /api/categories");
        return ResponseEntity.ok(serviceCategory.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar categoría por ID", description = "Obtiene una categoría mediante su identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoría encontrada"),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ModelCategory> getCategoryById(
            @Parameter(description = "Identificador de la categoría", example = "1", required = true)
            @PathVariable Long id) {
        log.info("GET /api/categories/{}", id);
        return ResponseEntity.ok(serviceCategory.findById(id));
    }

    @PostMapping
    @Operation(summary = "Crear categoría", description = "Registra una nueva categoría bibliográfica.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Categoría creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de la categoría inválidos"),
            @ApiResponse(responseCode = "409", description = "La categoría ya existe"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ModelCategory> createCategory(@Valid @RequestBody DtoCategory dtoCategory) {
        log.info("POST /api/categories");
        ModelCategory category = serviceCategory.save(dtoCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar categoría", description = "Modifica los datos de una categoría existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoría actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de la categoría inválidos"),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada"),
            @ApiResponse(responseCode = "409", description = "Conflicto con otra categoría existente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ModelCategory> updateCategory(
            @Parameter(description = "Identificador de la categoría", example = "1", required = true)
            @PathVariable Long id,
            @Valid @RequestBody DtoCategory dtoCategory) {

        log.info("PUT /api/categories/{}", id);
        return ResponseEntity.ok(serviceCategory.update(id, dtoCategory));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Desactivar categoría", description = "Desactiva lógicamente una categoría existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoría desactivada correctamente"),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<String> deleteCategory(
            @Parameter(description = "Identificador de la categoría", example = "1", required = true)
            @PathVariable Long id) {
        log.info("DELETE /api/categories/{}", id);
        serviceCategory.delete(id);
        return ResponseEntity.ok("Categoría desactivada correctamente con ID: " + id);
    }

    @PatchMapping("/{id}/activate")
    @Operation(summary = "Activar categoría", description = "Reactiva una categoría previamente desactivada.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoría activada correctamente"),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ModelCategory> activateCategory(
            @Parameter(description = "Identificador de la categoría", example = "1", required = true)
            @PathVariable Long id) {
        log.info("PATCH /api/categories/{}/activate", id);
        ModelCategory category = serviceCategory.activate(id);
        return ResponseEntity.ok(category);
    }
}
