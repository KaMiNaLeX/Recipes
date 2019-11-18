package com.samsolutions.recipes.service;

import com.samsolutions.recipes.model.CategoryEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * User service.
 *
 * @author kaminskiy.alexey
 * @since 2019.11
 */
@Service
public interface CategoryService {
    CategoryEntity createCategory(CategoryEntity categoryEntity);

    CategoryEntity updateCategory(UUID uuid, CategoryEntity categoryEntity);

    void removeById(UUID uuid);

    List<CategoryEntity> findAll();

    CategoryEntity getById(UUID uuid);

}
