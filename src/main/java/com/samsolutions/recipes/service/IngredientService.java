package com.samsolutions.recipes.service;

import com.samsolutions.recipes.model.IngredientEntity;

import java.util.List;
import java.util.UUID;

/**
 * @author kaminskiy.alexey
 * @since 2019.11
 */
public interface IngredientService {

    IngredientEntity createIngredient(IngredientEntity ingredient);

    List<IngredientEntity> findAll();

    IngredientEntity updateIngredient(UUID uuid, IngredientEntity ingredient);

    void removeById(UUID uuid);

    IngredientEntity getById(UUID uuid);
}
