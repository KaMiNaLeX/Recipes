package com.samsolutions.recipes.service.impl;

import com.samsolutions.recipes.model.IngredientEntity;
import com.samsolutions.recipes.repository.IngredientRepository;
import com.samsolutions.recipes.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author kaminskiy.alexey
 * @since 2019.11
 */
@Service
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Override
    public IngredientEntity createIngredient(IngredientEntity ingredient) {
        IngredientEntity saveIngredient = new IngredientEntity();
        saveIngredient.setName(ingredient.getName());
        saveIngredient.setCalories(ingredient.getCalories());
        saveIngredient.setType(ingredient.getType());
        return ingredientRepository.save(saveIngredient);
    }

    @Override
    public List<IngredientEntity> findAll() {
        return ingredientRepository.findAll();
    }

    @Override
    public IngredientEntity updateIngredient(UUID uuid, IngredientEntity ingredient) {
        IngredientEntity updateIngredient = ingredientRepository.getById(uuid);
        updateIngredient.setName(ingredient.getName());
        updateIngredient.setCalories(ingredient.getCalories());
        updateIngredient.setType(ingredient.getType());
        return ingredientRepository.save(updateIngredient);
    }

    @Override
    public void removeById(UUID uuid) {
        IngredientEntity removeIngredient = ingredientRepository.getById(uuid);
        ingredientRepository.delete(removeIngredient);
    }

    @Override
    public IngredientEntity getById(UUID uuid) {
        return ingredientRepository.getById(uuid);
    }
}
