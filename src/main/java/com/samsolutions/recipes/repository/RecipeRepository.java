package com.samsolutions.recipes.repository;

import com.samsolutions.recipes.model.RecipeEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * @author kaminskiy.alexey
 * @since 2019.12
 */
@Repository
public interface RecipeRepository extends BaseRepository<RecipeEntity> {

    List<RecipeEntity> getByAuthorId(UUID authorId);

    List<RecipeEntity> findAllByName(String name);
}
