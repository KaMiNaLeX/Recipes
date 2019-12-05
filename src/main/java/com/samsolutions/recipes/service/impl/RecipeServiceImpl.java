package com.samsolutions.recipes.service.impl;

import com.samsolutions.recipes.model.RecipeEntity;
import com.samsolutions.recipes.repository.RecipeRepository;
import com.samsolutions.recipes.repository.UserRepository;
import com.samsolutions.recipes.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}
