package com.samsolutions.recipes.repository;

import com.samsolutions.recipes.model.RecipeIngredientEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * @author kaminskiy.alexey
 * @since 2019.12
 */
@Repository
public interface RecipeIngredientRepository extends BaseRepository<RecipeIngredientEntity> {

    List<RecipeIngredientEntity> findAllByRecipeId(UUID recipeId);
}
