package com.samsolutions.recipes.service.impl;

import com.samsolutions.recipes.dto.CookingStepDTO;
import com.samsolutions.recipes.model.CookingStepsEntity;
import com.samsolutions.recipes.repository.CookingStepsRepository;
import com.samsolutions.recipes.repository.RecipeRepository;
import com.samsolutions.recipes.service.CookingStepsService;
import com.samsolutions.recipes.service.ModelMapperService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;


/**
 * @author kaminskiy.alexey
 * @since 2019.11
 */
@Service
public class CookingStepsServiceImpl implements CookingStepsService, ModelMapperService {

    @Autowired
    private CookingStepsRepository cookingStepsRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Override
    @Transactional
    public CookingStepsEntity createStep(CookingStepsEntity cookingStepsEntity) throws IOException {
        try (InputStream inputStream = getClass().getResourceAsStream("/static/img/test.png")) {
            cookingStepsEntity.setActive(true);
            cookingStepsEntity.setContent(IOUtils.toByteArray(inputStream));
            cookingStepsEntity.setRecipe(recipeRepository.getById(cookingStepsEntity.getRecipeId()));
            cookingStepsRepository.save(cookingStepsEntity);
            return cookingStepsEntity;
        }
    }

    //todo: need to fix
    @Override
    @Transactional
    public CookingStepDTO createStepDTO(CookingStepDTO cookingStepDTO) throws IOException {
        try (InputStream inputStream = getClass().getResourceAsStream("/static/img/test.png")) {
            CookingStepsEntity cookingStepsEntity = new CookingStepsEntity();
            cookingStepsEntity.setContent(IOUtils.toByteArray(inputStream));
            cookingStepsEntity.setActive(true);
            cookingStepsEntity.setRecipe(recipeRepository.getById(cookingStepDTO.getRecipeId()));
            map(cookingStepDTO, cookingStepsEntity);
            map((cookingStepsRepository.save(cookingStepsEntity)), cookingStepDTO);
            return cookingStepDTO;
        }
    }


    @Override
    @Transactional
    public CookingStepsEntity updateStep(UUID id, CookingStepsEntity cookingStepsEntity) throws IOException {
        try (InputStream inputStream = getClass().getResourceAsStream("/static/img/test.png")) {
            CookingStepsEntity updateStep = cookingStepsRepository.getById(id);
            updateStep.setName(cookingStepsEntity.getName());
            updateStep.setDescription(cookingStepsEntity.getDescription());
            updateStep.setNumber(cookingStepsEntity.getNumber());
            updateStep.setActive(cookingStepsEntity.isActive());
            updateStep.setRecipeId(cookingStepsEntity.getRecipeId());
            cookingStepsEntity.setRecipe(recipeRepository.getById(cookingStepsEntity.getRecipeId()));
            updateStep.setContent(IOUtils.toByteArray(inputStream));
            cookingStepsRepository.save(updateStep);
            return updateStep;
        }
    }


    @Override
    @Transactional
    public void removeStepById(UUID uuid) {
        CookingStepsEntity deleteStep = cookingStepsRepository.getById(uuid);
        cookingStepsRepository.delete(deleteStep);
    }

    @Override
    public List<CookingStepsEntity> findAll() {
        return cookingStepsRepository.findAll();
    }

    @Override
    public CookingStepsEntity getById(UUID uuid) {
        return cookingStepsRepository.getById(uuid);
    }
}
