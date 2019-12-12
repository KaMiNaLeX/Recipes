package com.samsolutions.recipes.service;

import com.samsolutions.recipes.dto.RecipeDTO;
import com.samsolutions.recipes.dto.createRecipe.CreateRecipeDTO;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @author kaminskiy.alexey
 * @since 2019.12
 */
public interface RecipeService {

    List<RecipeDTO> findAll();

    RecipeDTO create(RecipeDTO recipeDTO);

    RecipeDTO update(UUID uuid, RecipeDTO recipeDTO);

    CreateRecipeDTO updateRecipe(UUID uuid, CreateRecipeDTO recipeDTO) throws IOException;

    void positiveVote(UUID uuid);

    void negativeVote(UUID uuid);

    void removeById(UUID uuid);

    List<RecipeDTO> getByCategoryName(String categoryName);

    CreateRecipeDTO createRecipeDTO(CreateRecipeDTO createRecipeDTO) throws IOException;

    CreateRecipeDTO getByRecipeId(UUID uuid);

    List<CreateRecipeDTO> getByAuthorId(UUID authorId);
}
