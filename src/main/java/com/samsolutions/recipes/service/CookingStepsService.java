package com.samsolutions.recipes.service;

import com.samsolutions.recipes.dto.CookingStepDTO;
import com.samsolutions.recipes.dto.createRecipe.CookingStepRecipeDTO;
import com.samsolutions.recipes.model.CookingStepsEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @author kaminskiy.alexey
 * @since 2019.11
 */

public interface CookingStepsService {

    CookingStepDTO createStepDTO(CookingStepDTO cookingStepDTO) throws IOException;

    void removeStepById(UUID uuid);

    List<CookingStepsEntity> findAll();

    List<CookingStepDTO> findAllDto();

    CookingStepsEntity getById(UUID uuid);

    CookingStepRecipeDTO savePhoto(UUID id, MultipartFile file) throws IOException;

}
