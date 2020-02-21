package com.samsolutions.recipes.repository;

import com.samsolutions.recipes.model.Enum.CookingDifficulty;
import com.samsolutions.recipes.model.RecipeEntity;
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
public interface RecipeRepository extends BaseRepository<RecipeEntity> {

    List<RecipeEntity> getByAuthorId(UUID authorId);

    List<RecipeEntity> findAllByName(String name);

    RecipeEntity getByNameAndAuthorId(String name, UUID uuid);

    List<RecipeEntity> findAllByCookingTimeBetween(int start, int end);

    List<RecipeEntity> findAllByCookingDifficulty(CookingDifficulty dif);

    List<RecipeEntity> findAllByCookingTimeBetweenAndCookingDifficulty(int start, int end, CookingDifficulty dif);
}
