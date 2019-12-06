package com.samsolutions.recipes.repository;

import com.samsolutions.recipes.model.CategoryRecipeEntity;

import java.util.List;
import java.util.UUID;

/**
 * @author kaminskiy.alexey
 * @since 2019.12
 */
public interface CategoryRecipeRepository extends BaseRepository<CategoryRecipeEntity> {
    List<CategoryRecipeEntity> findAllByCategoryId(UUID uuid);
}
