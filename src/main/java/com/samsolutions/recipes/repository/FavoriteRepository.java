package com.samsolutions.recipes.repository;

import com.samsolutions.recipes.DTO.RecipeDTO;
import com.samsolutions.recipes.model.FavoriteEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author kaminskiy.alexey
 * @since 2019.12
 */
@Repository
public interface FavoriteRepository extends BaseRepository<FavoriteEntity> {

    @Query(value = "SELECT r.name as " + RecipeDTO.NAME + ","
            + " r.cooking_difficulty as " + RecipeDTO.COOKINGDIFFICULTY + ","
            + " r.cooking_time as " + RecipeDTO.COOKINGTIME + ","
            + " r.last_modified as " + RecipeDTO.LASTMODIFIED + ","
            + " r.negative_votes as " + RecipeDTO.NEGATIVEVOTES + ","
            + " r.positive_votes as " + RecipeDTO.POSITIVEVOTES + ","
            + " u.login as " + RecipeDTO.AUTHOR
            + " FROM recipe r "
            + " inner join user u on r.author_id = u.id"
            + " inner join favorite f on u.id = f.user_id where f.id=:ID",
            countQuery = "SELECT COUNT(*) FROM recipe", nativeQuery = true)
    List<Map<String, Object>> getFavoritesById(@Param("ID") UUID uuid);
}
