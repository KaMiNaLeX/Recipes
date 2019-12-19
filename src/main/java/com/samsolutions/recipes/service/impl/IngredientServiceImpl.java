package com.samsolutions.recipes.service.impl;

import com.samsolutions.recipes.dto.IngredientDTO;
import com.samsolutions.recipes.model.IngredientEntity;
import com.samsolutions.recipes.repository.IngredientRepository;
import com.samsolutions.recipes.service.IngredientService;
import com.samsolutions.recipes.service.ModelMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/**
 * @author kaminskiy.alexey
 * @since 2019.11
 */
@Service
public class IngredientServiceImpl implements IngredientService, ModelMapperService {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Override
    public IngredientDTO createIngredient(IngredientDTO ingredient) {
        IngredientEntity saveIngredient = new IngredientEntity();
        map(ingredient, saveIngredient);
        map(ingredientRepository.save(saveIngredient), ingredient);
        return ingredient;
    }

    @Override
    public List<IngredientDTO> findAll() {
        List<IngredientEntity> sortedList = ingredientRepository.findAll();
        sortedList.sort(new Comparator<IngredientEntity>() {
            @Override
            public int compare(IngredientEntity o1, IngredientEntity o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        List<IngredientDTO> ingredientDTOList = new ArrayList<>();
        IngredientDTO ingredientDTO = new IngredientDTO();
        ingredientDTOList.add(ingredientDTO);
        map(sortedList, ingredientDTOList);
        return ingredientDTOList;
    }

    @Override
    @Transactional
    public IngredientDTO updateIngredient(UUID uuid, IngredientDTO ingredient) {
        IngredientEntity updateIngredient = ingredientRepository.getById(uuid);
        ingredient.setId(updateIngredient.getId());
        map(ingredient, updateIngredient);
        map(ingredientRepository.save(updateIngredient), ingredient);
        return ingredient;
    }

    @Override
    @Transactional
    public void removeById(UUID uuid) {
        IngredientEntity removeIngredient = ingredientRepository.getById(uuid);
        ingredientRepository.delete(removeIngredient);
    }

    @Override
    public IngredientDTO getById(UUID uuid) {
        IngredientDTO ingredientDTO = new IngredientDTO();
        map(ingredientRepository.getById(uuid), ingredientDTO);
        return ingredientDTO;
    }
}
