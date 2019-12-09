package com.samsolutions.recipes.service;

import com.samsolutions.recipes.dto.CookingStepDTO;
import com.samsolutions.recipes.model.CookingStepsEntity;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @author kaminskiy.alexey
 * @since 2019.11
 */

public interface CookingStepsService {
    CookingStepsEntity createStep(CookingStepsEntity cookingStepsEntity) throws IOException;

    CookingStepDTO createStepDTO(CookingStepDTO cookingStepDTO) throws IOException;

    CookingStepsEntity updateStep(UUID id, CookingStepsEntity cookingStepsEntity) throws IOException;

    void removeStepById(UUID uuid);

    List<CookingStepsEntity> findAll();

    CookingStepsEntity getById(UUID uuid);

}
