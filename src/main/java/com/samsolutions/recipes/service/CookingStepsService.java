package com.samsolutions.recipes.service;

import com.samsolutions.recipes.model.CookingStepsEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author kaminskiy.alexey
 * @since 2019.11
 */
@Service
public interface CookingStepsService {
    CookingStepsEntity createStep(CookingStepsEntity cookingStepsEntity);

    CookingStepsEntity updateStep(UUID id, CookingStepsEntity cookingStepsEntity);

    void removeStepById(UUID uuid);

    List<CookingStepsEntity> findAll();

    CookingStepsEntity getById(UUID uuid);

}
