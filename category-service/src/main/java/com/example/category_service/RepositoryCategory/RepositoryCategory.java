package com.example.category_service.RepositoryCategory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.category_service.ModelCategory.ModelCategory;

@Repository
public interface RepositoryCategory extends JpaRepository<ModelCategory, Long> {

    Optional<ModelCategory> findByName(String name);

}