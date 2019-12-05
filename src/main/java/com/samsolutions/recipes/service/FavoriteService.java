package com.samsolutions.recipes.service;

import com.samsolutions.recipes.DTO.FavoriteDTO;
import com.samsolutions.recipes.DTO.RecipeDTO;
import com.samsolutions.recipes.model.FavoriteEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author kaminskiy.alexey
 * @since 2019.12
 */
@Service
public interface FavoriteService {

    FavoriteEntity create(FavoriteEntity favoriteEntity);

    void removeById(UUID uuid);

    List<RecipeDTO> getFavoritesById(UUID uuid);

}
