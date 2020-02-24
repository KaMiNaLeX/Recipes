package com.samsolutions.recipes.service;

import com.samsolutions.recipes.dto.RecipeDTO;
import com.samsolutions.recipes.dto.UserDTO;
import com.samsolutions.recipes.dto.createRecipe.CookingStepRecipeDTO;
import com.samsolutions.recipes.dto.createRecipe.CreateRecipeDTO;
import com.samsolutions.recipes.dto.createRecipe.IngredientRecipeDTO;
import com.samsolutions.recipes.dto.findByData.RecipeDataDTO;
import com.samsolutions.recipes.dto.findByIngredients.IngredientNameListDTO;
import com.samsolutions.recipes.model.RecipeEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @author kaminskiy.alexey
 * @since 2019.12
 */
public interface RecipeService {

    List<RecipeDTO> findAll();

    RecipeDTO create(RecipeDTO recipeDTO);

    RecipeDTO update(UUID uuid, RecipeDTO recipeDTO);

    RecipeDTO findByRecipeId(UUID uuid);

    CreateRecipeDTO updateRecipe(UUID uuid, CreateRecipeDTO recipeDTO);

    void positiveVote(UUID uuid);

    void negativeVote(UUID uuid);

    void removeById(UUID uuid);

    List<RecipeDTO> getByCategoryName(String categoryName);

    List<RecipeDTO> getByCategory(UUID categoryId);

    CreateRecipeDTO createRecipeDTO(CreateRecipeDTO createRecipeDTO);

    CreateRecipeDTO getByRecipeId(UUID uuid);

    List<CreateRecipeDTO> getByAuthorId(UUID authorId);

    List<RecipeDTO> getByAuthorName(String name);

    List<RecipeDTO> findAllByName(String name);

    RecipeDTO getByNameAuthorId(String name, UUID uuid);

    List<RecipeDTO> findAllByIngredient(IngredientNameListDTO ingredientNameListDTO);

    List<RecipeDTO> findAllByData(RecipeDataDTO recipeDataDTO);

    RecipeDTO savePhoto(UUID id, MultipartFile file) throws IOException;

    UserDTO getAuthorName(String authorId);

    //for createRecipe
    void saveCategoryRecipeEntityList(CreateRecipeDTO createRecipeDTO);

    List<CookingStepRecipeDTO> saveCookingStepsEntityList(CreateRecipeDTO createRecipeDTO, RecipeEntity recipeEntity);

    void saveRecipeIngredientList(CreateRecipeDTO createRecipeDTO, RecipeEntity recipeEntity);

    //for UpdateRecipe
    void updateCategory(CreateRecipeDTO createRecipeDTO, RecipeEntity recipeEntity);

    void updateCookingStepsEntityList(CreateRecipeDTO createRecipeDTO, RecipeEntity recipeEntity);

    void updateRecipeIngredientList(CreateRecipeDTO createRecipeDTO, RecipeEntity recipeEntity);

    //for getByRecipeId and getByAuthorId
    CreateRecipeDTO mapCategoryRecipeEntityListToDTO(UUID uuid, CreateRecipeDTO createRecipeDTO);

    CreateRecipeDTO mapCookingStepRecipeEntityListToDTO(RecipeEntity recipeEntity, CreateRecipeDTO createRecipeDTO);

    CreateRecipeDTO mapRecipeIngredientEntityListToDTO(RecipeEntity recipeEntity, CreateRecipeDTO createRecipeDTO);

}
