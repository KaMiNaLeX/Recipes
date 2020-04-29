package com.samsolutions.recipes.repository;

import com.samsolutions.recipes.model.RecipeVotesEntity;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author Kaminskiy Alexey
 * @since 2020.03
 **/
@Repository
public interface RecipeVotesRepository extends BaseRepository<RecipeVotesEntity> {
    RecipeVotesEntity getByUserIdAndRecipeId(UUID userId, UUID recipeId);
}
