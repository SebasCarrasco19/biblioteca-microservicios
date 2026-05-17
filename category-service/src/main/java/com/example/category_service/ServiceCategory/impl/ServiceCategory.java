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
        log.info("Obteniendo todas las categorias");
        return repositoryCategory.findAll();
    }

    @Override
    public ModelCategory findById(Long id) {
        log.info("Buscando categoria con ID: {}", id);

        return repositoryCategory.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontro la categoria con ID: " + id));
    }

    @Override
    public ModelCategory save(DtoCategory dtoCategory) {
        log.info("Guardando nueva categoria: {}", dtoCategory.getNombre());

        if (repositoryCategory.existsByNombre(dtoCategory.getNombre())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe una categoria con el nombre: " + dtoCategory.getNombre());
        }

        ModelCategory category = ModelCategory.builder()
                .nombre(dtoCategory.getNombre())
                .descripcion(dtoCategory.getDescripcion())
                .estado("ACTIVO")
                .build();

        return repositoryCategory.save(category);
    }

    @Override
    public ModelCategory update(Long id, DtoCategory dtoCategory) {
        log.info("Actualizando categoria con ID: {}", id);

        ModelCategory category = findById(id);

        if (repositoryCategory.existsByNombreAndIdNot(dtoCategory.getNombre(), id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe otra categoria con el nombre: " + dtoCategory.getNombre());
        }

        category.setNombre(dtoCategory.getNombre());
        category.setDescripcion(dtoCategory.getDescripcion());

        return repositoryCategory.save(category);
    }

    @Override
    public void delete(Long id) {
        log.info("Desactivando categoria con ID: {}", id);

        ModelCategory category = findById(id);

        if ("INACTIVO".equalsIgnoreCase(category.getEstado())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La categoria ya esta inactiva");
        }

        category.setEstado("INACTIVO");

        repositoryCategory.save(category);
    }

    @Override
    public ModelCategory activate(Long id) {
        log.info("Activando categoria con ID: {}", id);

        ModelCategory category = findById(id);

        if ("ACTIVO".equalsIgnoreCase(category.getEstado())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La categoria ya esta activa");
        }

        category.setEstado("ACTIVO");

        return repositoryCategory.save(category);
    }
}
