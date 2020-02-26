package com.samsolutions.recipes.repository;

import com.samsolutions.recipes.model.CategoryRecipeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * @author kaminskiy.alexey
 * @since 2019.12
 */
public interface CategoryRecipeRepository extends BaseRepository<CategoryRecipeEntity> {
    Page<CategoryRecipeEntity> findAllByCategoryId(UUID uuid, Pageable pageable);

    List<CategoryRecipeEntity> findAllByRecipeId(UUID recipeId);
}
