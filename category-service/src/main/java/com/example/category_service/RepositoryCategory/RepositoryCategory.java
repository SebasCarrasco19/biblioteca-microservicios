package com.example.category_service.RepositoryCategory;

import com.example.category_service.ModelCategory.ModelCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositoryCategory extends JpaRepository<ModelCategory, Long> {

    Optional<ModelCategory> findByNombre(String nombre);

    boolean existsByNombre(String nombre);

    boolean existsByNombreAndIdNot(String nombre, Long id);
}