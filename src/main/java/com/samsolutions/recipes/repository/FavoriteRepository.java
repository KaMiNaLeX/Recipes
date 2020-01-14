package com.samsolutions.recipes.repository;

import com.samsolutions.recipes.model.FavoriteEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * @author kaminskiy.alexey
 * @since 2019.12
 */
@Repository
public interface FavoriteRepository extends BaseRepository<FavoriteEntity> {

    List<FavoriteEntity> findAllByUserId(UUID uuid);
}
