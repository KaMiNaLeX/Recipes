package com.samsolutions.recipes.service;

import com.samsolutions.recipes.dto.FavoriteDTO;
import com.samsolutions.recipes.dto.RecipeDTO;
import com.samsolutions.recipes.dto.createFavorite.CreateFavoriteDTO;
import com.samsolutions.recipes.dto.createRecipe.CreateRecipeDTO;

import java.util.List;
import java.util.UUID;

/**
 * @author kaminskiy.alexey
 * @since 2019.12
 */
public interface FavoriteService {

    void removeById(UUID uuid);

    void removeByUserIdAndRecipeId(UUID userId, UUID recipeId);

    FavoriteDTO createDTO(CreateFavoriteDTO favoriteDTO);

    List<FavoriteDTO> findAllByUserId(UUID uuid, int page, int size, String sort);

    int getCountAllFavoritesRecipes(UUID userId);

    List<RecipeDTO> checkInFavorite(UUID userId, List<RecipeDTO> list);

    CreateRecipeDTO checkInFavorite(UUID userId, CreateRecipeDTO recipeDTO);

    RecipeDTO checkVotes(UUID userId, RecipeDTO recipeDTO);

}
