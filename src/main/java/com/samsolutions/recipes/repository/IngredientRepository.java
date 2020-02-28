package com.samsolutions.recipes.repository;

import com.samsolutions.recipes.model.Enum.Type;
import com.samsolutions.recipes.model.IngredientEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author kaminskiy.alexey
 * @since 2019.11
 */
@Repository
public interface IngredientRepository extends BaseRepository<IngredientEntity> {
    IngredientEntity getByName(String name);

    IngredientEntity getByNameRu(String name);

    List<IngredientEntity> findAllByType(Type type);
}
