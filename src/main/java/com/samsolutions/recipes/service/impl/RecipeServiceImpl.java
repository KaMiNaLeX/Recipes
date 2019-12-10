package com.samsolutions.recipes.service.impl;

import com.samsolutions.recipes.dto.RecipeDTO;
import com.samsolutions.recipes.dto.createRecipe.CreateRecipeDTO;
import com.samsolutions.recipes.exception.NotFoundException;
import com.samsolutions.recipes.model.CategoryEntity;
import com.samsolutions.recipes.model.CategoryRecipeEntity;
import com.samsolutions.recipes.model.CookingStepsEntity;
import com.samsolutions.recipes.model.IngredientEntity;
import com.samsolutions.recipes.model.RecipeEntity;
import com.samsolutions.recipes.model.RecipeIngredientEntity;
import com.samsolutions.recipes.repository.CategoryRecipeRepository;
import com.samsolutions.recipes.repository.CategoryRepository;
import com.samsolutions.recipes.repository.CookingStepsRepository;
import com.samsolutions.recipes.repository.IngredientRepository;
import com.samsolutions.recipes.repository.RecipeIngredientRepository;
import com.samsolutions.recipes.repository.RecipeRepository;
import com.samsolutions.recipes.repository.UserRepository;
import com.samsolutions.recipes.service.ModelMapperService;
import com.samsolutions.recipes.service.RecipeService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
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

    @Autowired
    private CookingStepsRepository cookingStepsRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private RecipeIngredientRepository recipeIngredientRepository;

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
    public CreateRecipeDTO createRecipeDTO(CreateRecipeDTO createRecipeDTO) throws IOException {
        createRecipeDTO.setNegativeVotes(0);
        createRecipeDTO.setPositiveVotes(0);
        //save RecipeEntity
        RecipeEntity recipeEntity = new RecipeEntity();
        map(createRecipeDTO, recipeEntity);
        map(recipeRepository.save(recipeEntity), createRecipeDTO);
        //save CategoryRecipeEntity
        CategoryEntity categoryEntity = categoryRepository.getByName(createRecipeDTO.getCategoryName());
        CategoryRecipeEntity categoryRecipeEntity = new CategoryRecipeEntity();
        categoryRecipeEntity.setCategoryId(categoryEntity.getId());
        categoryRecipeEntity.setRecipeId(recipeEntity.getId());
        map(categoryRecipeRepository.save(categoryRecipeEntity), createRecipeDTO);
        createRecipeDTO.setCategoryName(categoryEntity.getName());
        //save CookingStepsEntity
        try (InputStream inputStream = getClass().getResourceAsStream("/static/img/test.png")) {
            List<CookingStepsEntity> cookingStepsEntityList = new ArrayList<>();
            CookingStepsEntity cookingStepsEntity = new CookingStepsEntity();
            cookingStepsEntityList.add(cookingStepsEntity);
            map(createRecipeDTO.getCookingStepRecipeDTOList(), cookingStepsEntityList);
            for (int i = 0; i < cookingStepsEntityList.size(); i++) {
                cookingStepsEntityList.get(i).setActive(true);
                cookingStepsEntityList.get(i).setContent(IOUtils.toByteArray(inputStream));
                cookingStepsEntityList.get(i).setRecipeId(recipeEntity.getId());
                map(cookingStepsRepository.save(cookingStepsEntityList.get(i)), createRecipeDTO.getCookingStepRecipeDTOList());
            }
        }
        //save RecipeIngredient
        List<RecipeIngredientEntity> recipeIngredientEntityList = new ArrayList<>();
        RecipeIngredientEntity recipeIngredientEntity = new RecipeIngredientEntity();
        recipeIngredientEntityList.add(recipeIngredientEntity);
        map(createRecipeDTO.getIngredientRecipeDTOList(), recipeIngredientEntityList);
        for (int i = 0; i < recipeIngredientEntityList.size(); i++) {
            IngredientEntity ingredientEntity =
                    ingredientRepository.getByName(createRecipeDTO.getIngredientRecipeDTOList().get(i).getName());
            recipeIngredientEntityList.get(i).setIngredientId(ingredientEntity.getId());
            recipeIngredientEntityList.get(i).setRecipeId(recipeEntity.getId());
            map(recipeIngredientRepository.save(recipeIngredientEntityList.get(i)), createRecipeDTO.getIngredientRecipeDTOList());
        }

        return createRecipeDTO;
    }

}
