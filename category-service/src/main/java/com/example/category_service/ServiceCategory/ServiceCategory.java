package com.example.category_service.ServiceCategory;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.category_service.DtoCategory.DtoCategory;
import com.example.category_service.ModelCategory.ModelCategory;
import com.example.category_service.RepositoryCategory.RepositoryCategory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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

        log.info("Buscando categoría con ID {}", id);

        return repositoryCategory.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    }

    @Override
    public ModelCategory save(DtoCategory dtoCategory) {

        log.info("Guardando nueva categoría");

        ModelCategory category = ModelCategory.builder()
                .name(dtoCategory.getName())
                .description(dtoCategory.getDescription())
                .active(dtoCategory.getActive())
                .build();

        return repositoryCategory.save(category);
    }

    @Override
    public ModelCategory update(Long id, DtoCategory dtoCategory) {

        log.info("Actualizando categoría con ID {}", id);

        ModelCategory category = findById(id);

        category.setName(dtoCategory.getName());
        category.setDescription(dtoCategory.getDescription());
        category.setActive(dtoCategory.getActive());

        return repositoryCategory.save(category);
    }

    @Override
    public void delete(Long id) {

        log.info("Eliminando categoría con ID {}", id);

        repositoryCategory.deleteById(id);
    }
}