package com.samsolutions.recipes.service;

import com.samsolutions.recipes.dto.RecipeDTO;
import com.samsolutions.recipes.model.FavoriteEntity;

import java.util.List;
import java.util.UUID;

/**
 * @author kaminskiy.alexey
 * @since 2019.12
 */
public interface FavoriteService {

    FavoriteEntity create(FavoriteEntity favoriteEntity);

    void removeById(UUID uuid);

    List<RecipeDTO> getFavoritesById(UUID uuid);

}
