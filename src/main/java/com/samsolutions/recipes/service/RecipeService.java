package com.samsolutions.recipes.service;

import com.samsolutions.recipes.model.RecipeEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author kaminskiy.alexey
 * @since 2019.12
 */
@Service
public interface RecipeService {

    List<RecipeEntity> findAll();
}
