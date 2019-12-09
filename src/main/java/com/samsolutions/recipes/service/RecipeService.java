package com.samsolutions.recipes.service;

import com.samsolutions.recipes.model.RecipeEntity;

import java.util.List;
import java.util.UUID;

/**
 * @author kaminskiy.alexey
 * @since 2019.12
 */
public interface RecipeService {

    List<RecipeEntity> findAll();

    RecipeEntity create(RecipeEntity recipeEntity);

    RecipeEntity update(UUID uuid, RecipeEntity recipeEntity);

    void positiveVote(UUID uuid);

    void negativeVote(UUID uuid);

    void removeById(UUID uuid);

    List<RecipeEntity> getByCategoryName(String categoryName);
}
