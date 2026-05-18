package com.example.category_service.ServiceCategory.impl;

import com.example.category_service.DtoCategory.DtoCategory;
import com.example.category_service.ModelCategory.ModelCategory;
import com.example.category_service.RepositoryCategory.RepositoryCategory;
import com.example.category_service.ServiceCategory.ICategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceCategory implements ICategoryService {

    private final RepositoryCategory repositoryCategory;

    @Override
    public List<ModelCategory> findAll() {
        log.info("Obteniendo todas las categorías");
        return repositoryCategory.findAll();
    }

    @Override
    public ModelCategory findById(Long id) {
        log.info("Buscando categoría con ID: {}", id);

        return repositoryCategory.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No existe una categoría con ID: " + id
                ));
    }

    @Override
    public ModelCategory save(DtoCategory dtoCategory) {
        log.info("Guardando nueva categoría: {}", dtoCategory.getNombre());

        String nombre = dtoCategory.getNombre().trim();
        String descripcion = dtoCategory.getDescripcion().trim();

        if (repositoryCategory.existsByNombreIgnoreCase(nombre)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Ya existe una categoría con el nombre: " + nombre
            );
        }

        ModelCategory category = ModelCategory.builder()
                .nombre(nombre)
                .descripcion(descripcion)
                .estado("ACTIVO")
                .build();

        return repositoryCategory.save(category);
    }

    @Override
    public ModelCategory update(Long id, DtoCategory dtoCategory) {
        log.info("Actualizando categoría con ID: {}", id);

        ModelCategory category = findById(id);

        String nombre = dtoCategory.getNombre().trim();
        String descripcion = dtoCategory.getDescripcion().trim();

        if (repositoryCategory.existsByNombreIgnoreCaseAndIdNot(nombre, id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Ya existe otra categoría con el nombre: " + nombre
            );
        }

        category.setNombre(nombre);
        category.setDescripcion(descripcion);

        return repositoryCategory.save(category);
    }

    @Override
    public void delete(Long id) {
        log.info("Desactivando categoría con ID: {}", id);

        ModelCategory category = findById(id);

        if ("INACTIVO".equalsIgnoreCase(category.getEstado())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "La categoría ya está inactiva"
            );
        }

        category.setEstado("INACTIVO");

        repositoryCategory.save(category);
    }

    @Override
    public ModelCategory activate(Long id) {
        log.info("Activando categoría con ID: {}", id);

        ModelCategory category = findById(id);

        if ("ACTIVO".equalsIgnoreCase(category.getEstado())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "La categoría ya está activa"
            );
        }

        category.setEstado("ACTIVO");

        return repositoryCategory.save(category);
    }
}