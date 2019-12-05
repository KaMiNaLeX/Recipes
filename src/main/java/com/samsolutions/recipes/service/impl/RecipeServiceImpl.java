package com.samsolutions.recipes.service.impl;

import com.samsolutions.recipes.model.RecipeEntity;
import com.samsolutions.recipes.repository.RecipeRepository;
import com.samsolutions.recipes.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author kaminskiy.alexey
 * @since 2019.12
 */
@Service
public class RecipeServiceImpl implements RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;

    @Override
    public List<RecipeEntity> findAll() {
        return recipeRepository.findAll();
    }
}
