package com.samsolutions.recipes.service.impl;

import com.samsolutions.recipes.dto.RecipeDTO;
import com.samsolutions.recipes.dto.createRecipe.CreateRecipeDTO;
import com.samsolutions.recipes.exception.NotFoundException;
import com.samsolutions.recipes.model.CategoryEntity;
import com.samsolutions.recipes.model.CategoryRecipeEntity;
import com.samsolutions.recipes.model.RecipeEntity;
import com.samsolutions.recipes.repository.CategoryRecipeRepository;
import com.samsolutions.recipes.repository.CategoryRepository;
import com.samsolutions.recipes.repository.RecipeRepository;
import com.samsolutions.recipes.repository.UserRepository;
import com.samsolutions.recipes.service.ModelMapperService;
import com.samsolutions.recipes.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * @author kaminskiy.alexey
 * @since 2019.12
 */
@Service
public class RecipeServiceImpl implements RecipeService, ModelMapperService {
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryRecipeRepository categoryRecipeRepository;

    @Override
    public List<RecipeDTO> findAll() {
        List<RecipeDTO> recipeDTOList = new ArrayList<>();
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTOList.add(recipeDTO);
        map(recipeRepository.findAll(), recipeDTOList);
        return recipeDTOList;
    }

    @Override
    public RecipeDTO create(RecipeDTO recipeDTO) {
        recipeDTO.setNegativeVotes(0);
        recipeDTO.setPositiveVotes(0);
        RecipeEntity recipeEntity = new RecipeEntity();
        map(recipeDTO, recipeEntity);
        map(recipeRepository.save(recipeEntity), recipeDTO);
        return recipeDTO;
    }

    @Override
    @Transactional
    public RecipeDTO update(UUID uuid, RecipeDTO recipeDTO) {
        RecipeEntity updateEntity = recipeRepository.getById(uuid);
        recipeDTO.setId(updateEntity.getId());
        recipeDTO.setAuthorId(recipeDTO.getAuthorId());
        map(recipeDTO, updateEntity);
        map(recipeRepository.save(updateEntity), recipeDTO);
        return recipeDTO;
    }

    @Override
    @Transactional
    public void positiveVote(UUID uuid) {
        RecipeEntity updateEntity = recipeRepository.getById(uuid);
        updateEntity.setPositiveVotes(updateEntity.getPositiveVotes() + 1);
        recipeRepository.save(updateEntity);
    }

    @Override
    @Transactional
    public void negativeVote(UUID uuid) {
        RecipeEntity updateEntity = recipeRepository.getById(uuid);
        updateEntity.setNegativeVotes(updateEntity.getNegativeVotes() + 1);
        recipeRepository.save(updateEntity);
    }

    @Override
    @Transactional
    public void removeById(UUID uuid) {
        RecipeEntity removeEntity = recipeRepository.getById(uuid);
        recipeRepository.delete(removeEntity);
    }


    @Override
    @Transactional
    public List<RecipeDTO> getByCategoryName(String categoryName) {
        try {
            List<RecipeDTO> recipeDTOList = new ArrayList<>();
            RecipeDTO recipeDTO = new RecipeDTO();
            recipeDTOList.add(recipeDTO);

            CategoryEntity category = categoryRepository.getByName(categoryName);
            List<CategoryRecipeEntity> categoryRecipeEntityList =
                    categoryRecipeRepository.findAllByCategoryId(category.getId());
            List<RecipeEntity> recipeEntityList = new ArrayList<>();
            for (int i = 0; i < categoryRecipeEntityList.size(); i++) {
                RecipeEntity recipeEntity = recipeRepository.getById(categoryRecipeEntityList.get(i).getRecipeId());
                recipeEntityList.add(recipeEntity);
            }
            map(recipeEntityList, recipeDTOList);
            return recipeDTOList;
        } catch (NotFoundException | NullPointerException ex) {
            throw new NotFoundException("NOT_FOUND");
        }

    }

    @Override
    @Transactional
    public CreateRecipeDTO createRecipeDTO(CreateRecipeDTO createRecipeDTO) {
        return null;
    }

}
