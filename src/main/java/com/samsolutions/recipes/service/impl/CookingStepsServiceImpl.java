package com.samsolutions.recipes.service.impl;

import com.samsolutions.recipes.model.CookingStepsEntity;
import com.samsolutions.recipes.repository.CookingStepsRepository;
import com.samsolutions.recipes.service.CookingStepsService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    //todo: need to fix
    @Override
    public CookingStepsEntity createStep(CookingStepsEntity cookingStepsEntity) {
        try {
            CookingStepsEntity step = new CookingStepsEntity();
            step.setNumber(cookingStepsEntity.getNumber());
            step.setName(cookingStepsEntity.getName());
            step.setDescription(cookingStepsEntity.getDescription());
            step.setContent(IOUtils.toByteArray(getClass().getResourceAsStream("/static/img/test.png")));
            return step;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public CookingStepsEntity updateStep(UUID id, CookingStepsEntity cookingStepsEntity) {
        CookingStepsEntity updateStep = cookingStepsRepository.getById(id);
        updateStep.setName(cookingStepsEntity.getName());
        updateStep.setDescription(cookingStepsEntity.getDescription());
        updateStep.setNumber(cookingStepsEntity.getNumber());
        try {
            updateStep.setContent(IOUtils.toByteArray(getClass().getResourceAsStream("/static/img/test.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        cookingStepsRepository.save(updateStep);
        return updateStep;
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
