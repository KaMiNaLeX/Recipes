package com.samsolutions.recipes.service.impl;

import com.samsolutions.recipes.model.CookingStepsEntity;
import com.samsolutions.recipes.repository.CookingStepsRepository;
import com.samsolutions.recipes.service.CookingStepsService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

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
}
