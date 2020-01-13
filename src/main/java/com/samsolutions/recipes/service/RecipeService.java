package com.samsolutions.recipes.service;

import com.samsolutions.recipes.dto.RecipeDTO;
import com.samsolutions.recipes.dto.UserDTO;
import com.samsolutions.recipes.dto.createRecipe.CreateRecipeDTO;
import com.samsolutions.recipes.dto.findByData.RecipeDataDTO;
import com.samsolutions.recipes.dto.findByIngredients.IngredientNameListDTO;
import org.springframework.http.ResponseEntity;
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

    CreateRecipeDTO updateRecipe(UUID uuid, CreateRecipeDTO recipeDTO) throws IOException;

    void positiveVote(UUID uuid);

    void negativeVote(UUID uuid);

    void removeById(UUID uuid);

    List<RecipeDTO> getByCategoryName(String categoryName);

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
}
