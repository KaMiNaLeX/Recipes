package com.samsolutions.recipes.service.impl;

import com.samsolutions.recipes.dto.CookingStepDTO;
import com.samsolutions.recipes.dto.createRecipe.CookingStepRecipeDTO;
import com.samsolutions.recipes.model.CookingStepsEntity;
import com.samsolutions.recipes.repository.CookingStepsRepository;
import com.samsolutions.recipes.service.CookingStepsService;
import com.samsolutions.recipes.service.FileStorageService;
import com.samsolutions.recipes.service.ModelMapperService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


/**
 * @author kaminskiy.alexey
 * @since 2019.11
 */
@Log4j2
@Service
public class CookingStepsServiceImpl extends ModelMapperService implements CookingStepsService {

    @Autowired
    private CookingStepsRepository cookingStepsRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Override
    @Transactional
    public CookingStepDTO createStepDTO(CookingStepDTO cookingStepDTO) {
        CookingStepsEntity cookingStepsEntity = new CookingStepsEntity();
        map(cookingStepDTO, cookingStepsEntity);
        cookingStepsEntity.setActive(true);
        cookingStepsRepository.save(cookingStepsEntity);
        cookingStepDTO.setId(cookingStepsEntity.getId());
        return cookingStepDTO;
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
    public List<CookingStepDTO> findAllDto() {
        return mapListLambda(cookingStepsRepository.findAll(), CookingStepDTO.class);
    }

    @Override
    public CookingStepsEntity getById(UUID uuid) {
        return cookingStepsRepository.getById(uuid);
    }

    @Override
    public CookingStepRecipeDTO savePhoto(UUID id, MultipartFile file) throws IOException {
        CookingStepsEntity stepsEntity = cookingStepsRepository.getById(id);
        String imageSrc = fileStorageService.storeFile(file);
        stepsEntity.setImgSource(imageSrc);
        CookingStepRecipeDTO stepRecipeDTO = new CookingStepRecipeDTO();
        map(cookingStepsRepository.save(stepsEntity), stepRecipeDTO);
        return stepRecipeDTO;
    }
}
