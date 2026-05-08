package com.example.category_service.ServiceCategory;

import java.util.List;

import com.example.category_service.DtoCategory.DtoCategory;
import com.example.category_service.ModelCategory.ModelCategory;

public interface ICategoryService {

    List<ModelCategory> findAll();

    ModelCategory findById(Long id);

    ModelCategory save(DtoCategory dtoCategory);

    ModelCategory update(Long id, DtoCategory dtoCategory);

    void delete(Long id);
}