package com.samsolutions.recipes.service;

import com.samsolutions.recipes.dto.CategoryDTO;
import com.samsolutions.recipes.dto.createRecipe.CategoryRecipeDTO;

import java.util.List;
import java.util.UUID;

/**
 * User service.
 *
 * @author kaminskiy.alexey
 * @since 2019.11
 */
public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO updateCategory(UUID uuid, CategoryDTO categoryDTO);

    void removeById(UUID uuid);

    List<CategoryDTO> findAll();

    List<CategoryRecipeDTO> findAllCategoriesDTO();

    CategoryDTO getById(UUID uuid);

    CategoryDTO getByName(String name);

}
