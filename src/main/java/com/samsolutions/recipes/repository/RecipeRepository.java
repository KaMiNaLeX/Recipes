package com.samsolutions.recipes.repository;

import com.samsolutions.recipes.model.Enum.CookingDifficulty;
import com.samsolutions.recipes.model.RecipeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author kaminskiy.alexey
 * @since 2019.12
 */
@Repository
public interface RecipeRepository extends BaseRepository<RecipeEntity> {

    Page<RecipeEntity> getByAuthorId(UUID authorId, Pageable pageable);

    Page<RecipeEntity> findAllByName(String name, Pageable pageable);

    Page<RecipeEntity> findAllByNameRu(String name, Pageable pageable);

    RecipeEntity getByNameAndAuthorId(String name, UUID uuid);

    Page<RecipeEntity> findAllByCookingTimeBetween(int start, int end, Pageable pageable);

    Page<RecipeEntity> findAllByCookingDifficulty(CookingDifficulty dif, Pageable pageable);

    Page<RecipeEntity> findAllByCookingDifficultyRu(CookingDifficulty dif, Pageable pageable);

    Page<RecipeEntity> findAllByCookingTimeBetweenAndCookingDifficulty(int start, int end, CookingDifficulty dif, Pageable pageable);

    Page<RecipeEntity> findAllByCookingTimeBetweenAndCookingDifficultyRu(int start, int end, CookingDifficulty dif, Pageable pageable);
}
