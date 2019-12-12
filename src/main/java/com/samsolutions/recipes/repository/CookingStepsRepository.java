package com.samsolutions.recipes.repository;

import com.samsolutions.recipes.model.CookingStepsEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * @author kaminskiy.alexey
 * @since 2019.11
 */
@Repository
public interface CookingStepsRepository extends BaseRepository<CookingStepsEntity> {

    List<CookingStepsEntity> findAllByRecipeId(UUID uuid);
}
