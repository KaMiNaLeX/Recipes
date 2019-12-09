package com.samsolutions.recipes.service.impl;

import com.samsolutions.recipes.exception.NotFoundException;
import com.samsolutions.recipes.model.CategoryEntity;
import com.samsolutions.recipes.model.CategoryRecipeEntity;
import com.samsolutions.recipes.model.RecipeEntity;
import com.samsolutions.recipes.repository.CategoryRecipeRepository;
import com.samsolutions.recipes.repository.CategoryRepository;
import com.samsolutions.recipes.repository.RecipeRepository;
import com.samsolutions.recipes.repository.UserRepository;
import com.samsolutions.recipes.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author kaminskiy.alexey
 * @since 2019.12
 */
@Service
public class RecipeServiceImpl implements RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryRecipeRepository categoryRecipeRepository;

    @Override
    public List<RecipeEntity> findAll() {
        return recipeRepository.findAll();
    }

    @Override
    public RecipeEntity create(RecipeEntity recipeEntity) {
        recipeEntity.setNegativeVotes(0);
        recipeEntity.setPositiveVotes(0);
        recipeEntity.setUser(userRepository.getById(recipeEntity.getAuthorId()));
        return recipeRepository.save(recipeEntity);
    }

    //todo: why AuthorId = null?
    @Override
    public RecipeEntity update(UUID uuid, RecipeEntity recipeEntity) {
        RecipeEntity updateEntity = recipeRepository.getById(uuid);
        updateEntity.setPositiveVotes(recipeEntity.getPositiveVotes());
        updateEntity.setNegativeVotes(recipeEntity.getNegativeVotes());
        updateEntity.setAuthorId(recipeEntity.getAuthorId());
        updateEntity.setCookingDifficulty(recipeEntity.getCookingDifficulty());
        updateEntity.setCookingTime(recipeEntity.getCookingTime());
        updateEntity.setName(recipeEntity.getName());
        updateEntity.setUser(userRepository.getById(recipeEntity.getAuthorId()));
        return recipeRepository.save(updateEntity);
    }

    @Override
    public void positiveVote(UUID uuid) {
        RecipeEntity updateEntity = recipeRepository.getById(uuid);
        updateEntity.setPositiveVotes(updateEntity.getPositiveVotes() + 1);
        recipeRepository.save(updateEntity);
    }

    @Override
    public void negativeVote(UUID uuid) {
        RecipeEntity updateEntity = recipeRepository.getById(uuid);
        updateEntity.setNegativeVotes(updateEntity.getNegativeVotes() + 1);
        recipeRepository.save(updateEntity);
    }

    @Override
    public void removeById(UUID uuid) {
        RecipeEntity removeEntity = recipeRepository.getById(uuid);
        recipeRepository.delete(removeEntity);
    }

    @Override
    public List<RecipeEntity> getByCategoryName(String categoryName) {
        try {
            CategoryEntity category = categoryRepository.getByName(categoryName);
            List<CategoryRecipeEntity> categoryRecipeEntityList =
                    categoryRecipeRepository.findAllByCategoryId(category.getId());
            List<RecipeEntity> recipeEntityList = new ArrayList<>();
            for (int i = 0; i < categoryRecipeEntityList.size(); i++) {
                RecipeEntity recipeEntity = recipeRepository.getById(categoryRecipeEntityList.get(i).getRecipeId());
                recipeEntityList.add(recipeEntity);
            }
            return recipeEntityList;
        } catch (NotFoundException | NullPointerException ex) {
            throw new NotFoundException("NOT_FOUND");
        }

    }


}
