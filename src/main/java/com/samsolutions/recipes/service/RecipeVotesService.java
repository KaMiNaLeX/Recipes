package com.samsolutions.recipes.service;

import com.samsolutions.recipes.dto.RecipeVotesDTO;

import java.util.UUID;

/**
 * @author Kaminskiy Alexey
 * @since 2020.03
 **/
public interface RecipeVotesService {
    RecipeVotesDTO create(RecipeVotesDTO recipeVotesDTO);

    RecipeVotesDTO update(UUID uuid, RecipeVotesDTO recipeVotesDTO);

    void delete(UUID uuid);
}
