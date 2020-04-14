package com.samsolutions.recipes.repository;

import com.samsolutions.recipes.model.FavoriteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * @author kaminskiy.alexey
 * @since 2019.12
 */
@Repository
public interface FavoriteRepository extends BaseRepository<FavoriteEntity> {
    Page<FavoriteEntity> findAllByUserId(UUID uuid, Pageable pageable);

    List<FavoriteEntity> findAllByUserId(UUID userId);

    FavoriteEntity getByUserIdAndRecipeId(UUID userId, UUID recipeId);
}
