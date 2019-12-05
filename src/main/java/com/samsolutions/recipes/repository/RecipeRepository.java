package com.samsolutions.recipes.repository;

import com.samsolutions.recipes.model.RecipeEntity;
import org.springframework.stereotype.Repository;

/**
 * @author kaminskiy.alexey
 * @since 2019.12
 */
@Repository
public interface RecipeRepository extends BaseRepository<RecipeEntity> {
}
