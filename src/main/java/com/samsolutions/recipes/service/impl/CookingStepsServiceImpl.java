package com.samsolutions.recipes.service.impl;

import com.samsolutions.recipes.model.CookingStepsEntity;
import com.samsolutions.recipes.repository.CookingStepsRepository;
import com.samsolutions.recipes.service.CookingStepsService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

/**
 * @author kaminskiy.alexey
 * @since 2019.11
 */
@Service
public class CookingStepsServiceImpl implements CookingStepsService {
    @Autowired
    CookingStepsRepository cookingStepsRepository;

    @Override
    public CookingStepsEntity createStep(CookingStepsEntity cookingStepsEntity) throws IOException {
        try (InputStream inputStream = getClass().getResourceAsStream("/static/img/test.png")) {
            CookingStepsEntity step = new CookingStepsEntity();
            step.setNumber(cookingStepsEntity.getNumber());
            step.setName(cookingStepsEntity.getName());
            step.setDescription(cookingStepsEntity.getDescription());
            step.setContent(IOUtils.toByteArray(inputStream));
            return step;
        }
    }

    @Override
    public CookingStepsEntity updateStep(UUID id, CookingStepsEntity cookingStepsEntity) throws IOException {
        try (InputStream inputStream = getClass().getResourceAsStream("/static/img/test.png")) {
            CookingStepsEntity updateStep = cookingStepsRepository.getById(id);
            updateStep.setName(cookingStepsEntity.getName());
            updateStep.setDescription(cookingStepsEntity.getDescription());
            updateStep.setNumber(cookingStepsEntity.getNumber());
            updateStep.setContent(IOUtils.toByteArray(inputStream));
            cookingStepsRepository.save(updateStep);
            return updateStep;
        }
    }

    @Override
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
