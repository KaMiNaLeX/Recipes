package com.samsolutions.recipes.service;

import com.samsolutions.recipes.dto.CategoryDTO;
import com.samsolutions.recipes.dto.IngredientDTO;
import com.samsolutions.recipes.model.Enum.Type;

import java.util.List;
import java.util.UUID;

/**
 * @author kaminskiy.alexey
 * @since 2019.11
 */
public interface IngredientService {

    IngredientDTO createIngredient(IngredientDTO ingredient);

    List<IngredientDTO> findAll();

    IngredientDTO updateIngredient(UUID uuid, IngredientDTO ingredient);

    boolean removeById(UUID uuid);

    IngredientDTO getById(UUID uuid);

    IngredientDTO getByName(String name);

    List<IngredientDTO> findByType(Type type);

    List<IngredientDTO> findAll(int page, int size, String sort);
}
