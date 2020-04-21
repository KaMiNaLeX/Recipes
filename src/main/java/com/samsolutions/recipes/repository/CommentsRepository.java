package com.samsolutions.recipes.repository;

import com.samsolutions.recipes.model.CommentsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author Kaminskiy Alexey
 * @since 2020.03
 **/
@Repository
public interface CommentsRepository extends BaseRepository<CommentsEntity> {

    Page<CommentsEntity> findAllByRecipeId(UUID recipeId, Pageable pageable);
}
