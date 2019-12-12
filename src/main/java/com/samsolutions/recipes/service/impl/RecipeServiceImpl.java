package com.samsolutions.recipes.service.impl;

import com.samsolutions.recipes.dto.RecipeDTO;
import com.samsolutions.recipes.dto.createRecipe.CategoryRecipeDTO;
import com.samsolutions.recipes.dto.createRecipe.CookingStepRecipeDTO;
import com.samsolutions.recipes.dto.createRecipe.CreateRecipeDTO;
import com.samsolutions.recipes.dto.createRecipe.IngredientRecipeDTO;
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
    public CreateRecipeDTO updateRecipe(UUID uuid, CreateRecipeDTO createRecipeDTO) throws IOException {
        RecipeEntity updateEntity = recipeRepository.getById(uuid);
        createRecipeDTO.setId(updateEntity.getId());
        map(createRecipeDTO, updateEntity);
        map(recipeRepository.save(updateEntity), createRecipeDTO);
        //update category
        List<CategoryEntity> categoryEntityList = new ArrayList<>();
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntityList.add(categoryEntity);
        map(createRecipeDTO.getCategoryRecipeDTOList(), categoryEntityList);

        List<CategoryRecipeEntity> updateCategoryRecipeList =
                categoryRecipeRepository.findAllByRecipeId(updateEntity.getId());
        for (int i = 0; i < updateCategoryRecipeList.size(); i++) {
            categoryRecipeRepository.delete(updateCategoryRecipeList.get(i));
        }
        for (int i = 0; i < categoryEntityList.size(); i++) {
            CategoryEntity categoryEntity1 = categoryRepository.getByName(categoryEntityList.get(i).getName());
            if (updateCategoryRecipeList.size() < categoryEntityList.size()) {
                CategoryRecipeEntity categoryRecipeEntity = new CategoryRecipeEntity();
                categoryRecipeEntity.setRecipeId(updateEntity.getId());
                categoryRecipeEntity.setCategoryId(categoryEntity1.getId());
                updateCategoryRecipeList.add(categoryRecipeEntity);
                int lastIndex = updateCategoryRecipeList.size() - 1;
                categoryRecipeRepository.save(updateCategoryRecipeList.get(lastIndex));
            }

            updateCategoryRecipeList.get(i).setCategoryId(categoryEntity1.getId());
            updateCategoryRecipeList.get(i).setRecipeId(updateEntity.getId());
            map(categoryRecipeRepository.save(updateCategoryRecipeList.get(i)), createRecipeDTO.getCategoryRecipeDTOList());
        }
        //update CookingStepsEntityList
        try (InputStream inputStream = getClass().getResourceAsStream("/static/img/test.png")) {
            List<CookingStepsEntity> cookingStepsEntityList = new ArrayList<>();
            CookingStepsEntity cookingStepsEntity = new CookingStepsEntity();
            cookingStepsEntityList.add(cookingStepsEntity);
            map(createRecipeDTO.getCookingStepRecipeDTOList(), cookingStepsEntityList);

            List<CookingStepsEntity> updateCookingStepsList =
                    cookingStepsRepository.findAllByRecipeId(updateEntity.getId());
            for (int i = 0; i < updateCookingStepsList.size(); i++) {
                cookingStepsRepository.delete(updateCookingStepsList.get(i));
            }

            for (int i = 0; i < cookingStepsEntityList.size(); i++) {
                cookingStepsEntityList.get(i).setContent(IOUtils.toByteArray(inputStream));
                cookingStepsEntityList.get(i).setRecipeId(updateEntity.getId());
                map(cookingStepsRepository.save(cookingStepsEntityList.get(i)), createRecipeDTO.getCookingStepRecipeDTOList());
            }
        }
        //update RecipeIngredientList
        List<RecipeIngredientEntity> recipeIngredientEntityList = new ArrayList<>();
        RecipeIngredientEntity recipeIngredientEntity = new RecipeIngredientEntity();
        recipeIngredientEntityList.add(recipeIngredientEntity);
        map(createRecipeDTO.getIngredientRecipeDTOList(), recipeIngredientEntityList);

        List<RecipeIngredientEntity> updateRecipeIngredientList =
                recipeIngredientRepository.findAllByRecipeId(updateEntity.getId());
        for (int i = 0; i < updateRecipeIngredientList.size(); i++) {
            recipeIngredientRepository.delete(updateRecipeIngredientList.get(i));
        }
        for (int i = 0; i < recipeIngredientEntityList.size(); i++) {
            IngredientEntity ingredientEntity =
                    ingredientRepository.getByName(createRecipeDTO.getIngredientRecipeDTOList().get(i).getName());
            recipeIngredientEntityList.get(i).setIngredientId(ingredientEntity.getId());
            recipeIngredientEntityList.get(i).setRecipeId(updateEntity.getId());
            map(recipeIngredientRepository.save(recipeIngredientEntityList.get(i)), createRecipeDTO.getIngredientRecipeDTOList());
        }

        return createRecipeDTO;
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
        //save CategoryRecipeEntityList
        List<CategoryEntity> categoryEntityList = new ArrayList<>();
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntityList.add(categoryEntity);
        map(createRecipeDTO.getCategoryRecipeDTOList(), categoryEntityList);

        List<CategoryRecipeEntity> categoryRecipeEntityList = new ArrayList<>();
        for (int i = 0; i < categoryEntityList.size(); i++) {
            CategoryRecipeEntity categoryRecipeEntity = new CategoryRecipeEntity();
            categoryRecipeEntityList.add(categoryRecipeEntity);
            CategoryEntity categoryEntity1 = categoryRepository.getByName(categoryEntityList.get(i).getName());
            categoryRecipeEntityList.get(i).setCategoryId(categoryEntity1.getId());
            categoryRecipeEntityList.get(i).setRecipeId(recipeEntity.getId());
            map(categoryRecipeRepository.save(categoryRecipeEntityList.get(i)), createRecipeDTO.getCategoryRecipeDTOList());
        }
        //save CookingStepsEntityList
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
        //save RecipeIngredientList
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

    @Override
    public CreateRecipeDTO getByRecipeId(UUID uuid) {
        //map recipeEntity to DTO
        RecipeEntity recipeEntity = recipeRepository.getById(uuid);
        CreateRecipeDTO createRecipeDTO = new CreateRecipeDTO();
        map(recipeEntity, createRecipeDTO);
        //map categoryRecipeEntityList to DTO
        List<CategoryRecipeDTO> categoryRecipeDTOList = new ArrayList<>();
        List<CategoryRecipeEntity> categoryRecipeEntityList = categoryRecipeRepository.findAllByRecipeId(uuid);
        for (int i = 0; i < categoryRecipeEntityList.size(); i++) {
            CategoryEntity categoryEntity = categoryRecipeEntityList.get(i).getCategory();
            CategoryRecipeDTO categoryRecipeDTO = new CategoryRecipeDTO();
            map(categoryEntity, categoryRecipeDTO);
            categoryRecipeDTOList.add(categoryRecipeDTO);
            createRecipeDTO.setCategoryRecipeDTOList(categoryRecipeDTOList);
        }

        //map cookingStepRecipeEntityList to DTO
        CookingStepRecipeDTO cookingStepRecipeDTO = new CookingStepRecipeDTO();
        List<CookingStepRecipeDTO> cookingStepRecipeDTOList = new ArrayList<>();
        cookingStepRecipeDTOList.add(cookingStepRecipeDTO);
        createRecipeDTO.setCookingStepRecipeDTOList(cookingStepRecipeDTOList);
        map(recipeEntity.getCookingStepsEntityList(), createRecipeDTO.getCookingStepRecipeDTOList());
        //map recipeIngredientEntityList to DTO
        IngredientRecipeDTO ingredientRecipeDTO = new IngredientRecipeDTO();
        List<IngredientRecipeDTO> ingredientRecipeDTOList = new ArrayList<>();
        ingredientRecipeDTOList.add(ingredientRecipeDTO);
        createRecipeDTO.setIngredientRecipeDTOList(ingredientRecipeDTOList);

        List<RecipeIngredientEntity> recipeIngredientEntityList = recipeEntity.getRecipeIngredientEntityList();
        List<IngredientEntity> ingredientEntityList = new ArrayList<>();
        for (int i = 0; i < recipeIngredientEntityList.size(); i++) {
            IngredientEntity ingredientEntity = recipeIngredientEntityList.get(i).getIngredient();
            ingredientEntityList.add(ingredientEntity);
        }
        map(ingredientEntityList, createRecipeDTO.getIngredientRecipeDTOList());
        int size = createRecipeDTO.getIngredientRecipeDTOList().size();
        int countNull = size - 1;
        map(recipeEntity.getRecipeIngredientEntityList(), createRecipeDTO.getIngredientRecipeDTOList());
        for (int x = 0; x < countNull; x++) {
            createRecipeDTO.getIngredientRecipeDTOList().remove(size);
        }
        return createRecipeDTO;
    }

    @Override
    public List<CreateRecipeDTO> getByAuthorId(UUID authorId) {
        List<CreateRecipeDTO> createRecipeDTOList = new ArrayList<>();
        List<RecipeEntity> recipeEntityList = recipeRepository.getByAuthorId(authorId);
        for (int i = 0; i < recipeEntityList.size(); i++) {
            //map recipeEntity to DTO
            CreateRecipeDTO createRecipeDTO = new CreateRecipeDTO();
            map(recipeEntityList.get(i), createRecipeDTO);
            createRecipeDTOList.add(createRecipeDTO);
            //map categoryRecipeEntityList to DTO
            List<CategoryRecipeDTO> categoryRecipeDTOList = new ArrayList<>();
            List<CategoryRecipeEntity> categoryRecipeEntityList =
                    categoryRecipeRepository.findAllByRecipeId(recipeEntityList.get(i).getId());
            for (int j = 0; j < categoryRecipeEntityList.size(); j++) {
                CategoryEntity categoryEntity = categoryRecipeEntityList.get(j).getCategory();
                CategoryRecipeDTO categoryRecipeDTO = new CategoryRecipeDTO();
                map(categoryEntity, categoryRecipeDTO);
                categoryRecipeDTOList.add(categoryRecipeDTO);
                createRecipeDTO.setCategoryRecipeDTOList(categoryRecipeDTOList);
            }
            //map cookingStepRecipeEntityList to DTO
            CookingStepRecipeDTO cookingStepRecipeDTO = new CookingStepRecipeDTO();
            List<CookingStepRecipeDTO> cookingStepRecipeDTOList = new ArrayList<>();
            cookingStepRecipeDTOList.add(cookingStepRecipeDTO);
            createRecipeDTO.setCookingStepRecipeDTOList(cookingStepRecipeDTOList);
            map(recipeEntityList.get(i).getCookingStepsEntityList(), createRecipeDTO.getCookingStepRecipeDTOList());
            //map recipeIngredientEntityList to DTO
            IngredientRecipeDTO ingredientRecipeDTO = new IngredientRecipeDTO();
            List<IngredientRecipeDTO> ingredientRecipeDTOList = new ArrayList<>();
            ingredientRecipeDTOList.add(ingredientRecipeDTO);
            createRecipeDTO.setIngredientRecipeDTOList(ingredientRecipeDTOList);

            List<RecipeIngredientEntity> recipeIngredientEntityList = recipeEntityList.get(i).getRecipeIngredientEntityList();
            List<IngredientEntity> ingredientEntityList = new ArrayList<>();
            for (int j = 0; j < recipeIngredientEntityList.size(); j++) {
                IngredientEntity ingredientEntity = recipeIngredientEntityList.get(j).getIngredient();
                ingredientEntityList.add(ingredientEntity);
            }
            map(ingredientEntityList, createRecipeDTO.getIngredientRecipeDTOList());
            int size = createRecipeDTO.getIngredientRecipeDTOList().size();
            int countNull = size - 1;
            map(recipeEntityList.get(i).getRecipeIngredientEntityList(), createRecipeDTO.getIngredientRecipeDTOList());
            for (int x = 0; x < countNull; x++) {
                createRecipeDTO.getIngredientRecipeDTOList().remove(size);
            }
        }
        return createRecipeDTOList;
    }

}
