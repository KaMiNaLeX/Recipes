package com.samsolutions.recipes.service;

import com.samsolutions.recipes.model.CookingStepsEntity;
import org.springframework.stereotype.Service;

/**
 * @author kaminskiy.alexey
 * @since 2019.11
 */
@Service
public interface CookingStepsService {
    CookingStepsEntity createStep(CookingStepsEntity cookingStepsEntity);
}
