package com.samsolutions.recipes.service;

import com.samsolutions.recipes.dto.FavoriteDTO;
import com.samsolutions.recipes.dto.createFavorite.CreateFavoriteDTO;

import java.util.List;
import java.util.UUID;

/**
 * @author kaminskiy.alexey
 * @since 2019.12
 */
public interface FavoriteService {

    void removeById(UUID uuid);

    FavoriteDTO createDTO(CreateFavoriteDTO favoriteDTO);

    List<FavoriteDTO> findAllByUserId(UUID uuid, int page, int size, String sort);

    int getCountAllFavoritesRecipes(UUID userId);

}
