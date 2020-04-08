package com.samsolutions.recipes.controller;

import com.samsolutions.recipes.dto.RecipeDTO;
import com.samsolutions.recipes.dto.UserDTO;
import com.samsolutions.recipes.dto.createRecipe.CookingStepRecipeDTO;
import com.samsolutions.recipes.dto.createRecipe.CreateRecipeDTO;
import com.samsolutions.recipes.dto.findByData.RecipeDataDTO;
import com.samsolutions.recipes.dto.findByIngredients.IngredientNameListDTO;
import com.samsolutions.recipes.exception.CustomGlobalExceptionHandler;
import com.samsolutions.recipes.service.CookingStepsService;
import com.samsolutions.recipes.service.RecipeService;
import com.samsolutions.recipes.service.validation.ValidUUID;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @author kaminskiy.alexey
 * @since 2019.12
 */
@Log4j2
@Validated
@RestController
@RequestMapping("/api/recipe")
public class RecipeRestController extends CustomGlobalExceptionHandler {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private CookingStepsService cookingStepsService;

    @GetMapping("/")
    public List<RecipeDTO> findAll() {
        return recipeService.findAll();
    }

    @PostMapping("/create")
    public RecipeDTO create(@Valid @RequestBody RecipeDTO recipeDTO) {
        return recipeService.create(recipeDTO);
    }

    @PutMapping("/update/{id}")
    public RecipeDTO update(@PathVariable("id") @ValidUUID UUID uuid, @Valid @RequestBody RecipeDTO recipeDTO) {
        return recipeService.update(uuid, recipeDTO);
    }

    @PreAuthorize("hasAnyRole('ADMIN','AUTHOR')")
    @PutMapping("/updateRecipe/{id}")
    public CreateRecipeDTO updateRecipe(@PathVariable("id") @ValidUUID UUID uuid,
                                        @Valid @RequestBody CreateRecipeDTO recipeDTO) {
        try {
            return recipeService.updateRecipe(uuid, recipeDTO);
        } catch (Exception e) {
            log.error("Update recipe " + uuid + " is failed", e.fillInStackTrace());
            return null;
        }
    }

    @DeleteMapping("/delete/{id}")
    public void removeById(@PathVariable("id") @ValidUUID UUID uuid) {
        recipeService.removeById(uuid);
    }

    @PutMapping("/positive/{id}")
    public void positiveVote(@PathVariable("id") @ValidUUID UUID uuid) {
        recipeService.positiveVote(uuid);
    }

    @PutMapping("/negative/{id}")
    public void negativeVote(@PathVariable("id") @ValidUUID UUID uuid) {
        recipeService.negativeVote(uuid);
    }

    @GetMapping("/categoryName/{name}")
    public List<RecipeDTO> findAllByCategoryName(@PathVariable("name") @NotBlank String categoryName,
                                                 @RequestParam("page") int page,
                                                 @RequestParam("size") int size,
                                                 @RequestParam("sort") String sort) {
        return recipeService.getByCategoryName(categoryName, page, size, sort);
    }

    @PreAuthorize("hasAnyRole('ADMIN','AUTHOR')")
    @PostMapping("/createRecipe")
    public CreateRecipeDTO createRecipeDTO(@Valid @RequestBody CreateRecipeDTO createRecipeDTO) {
        try {
            return recipeService.createRecipeDTO(createRecipeDTO);
        } catch (NullPointerException e) {
            log.error("Create recipe " + createRecipeDTO.getName() + " is failed", e.fillInStackTrace());
            return null;
        }
    }

    @GetMapping("/id/{id}")
    public CreateRecipeDTO getByRecipeId(@PathVariable("id") @ValidUUID UUID uuid) {
        return recipeService.getByRecipeId(uuid);
    }

    @PreAuthorize("hasAnyRole('ADMIN','AUTHOR')")
    @GetMapping("/authorId/{id}")
    public List<CreateRecipeDTO> getByAuthorId(@PathVariable("id") @ValidUUID UUID uuid,
                                               @RequestParam("page") int page,
                                               @RequestParam("size") int size,
                                               @RequestParam("sort") String sort) {
        return recipeService.getByAuthorId(uuid, page, size, sort);
    }

    @GetMapping("/authorName/{name}")
    public List<RecipeDTO> getByAuthorName(@PathVariable("name") @NotBlank String name,
                                           @RequestParam("page") int page,
                                           @RequestParam("size") int size,
                                           @RequestParam("sort") String sort) {
        return recipeService.getByAuthorName(name, page, size, sort);
    }

    @GetMapping("/name/{name}")
    public List<RecipeDTO> findAllByName(@PathVariable("name") @NotBlank String name,
                                         @RequestParam("page") int page,
                                         @RequestParam("size") int size,
                                         @RequestParam("sort") String sort) {
        return recipeService.findAllByName(name, page, size, sort);
    }

    @GetMapping("/nameAndAuthor/{name}/{authorId}")
    public RecipeDTO getByNameAuthorId(@PathVariable("name") @NotBlank String name,
                                       @PathVariable("authorId") @ValidUUID UUID uuid) {
        try {
            return recipeService.getByNameAuthorId(name, uuid);
        } catch (Exception e) {
            log.error("getByNameAuthorId is failed", e.getCause());
            return null;
        }
    }

    @PostMapping("/ingredientName")
    public List<RecipeDTO> findAllByIngredientName(@Valid @RequestBody IngredientNameListDTO ingredientNameList,
                                                   @RequestParam("page") int page,
                                                   @RequestParam("size") int size,
                                                   @RequestParam("sort") String sort) {
        return recipeService.findAllByIngredient(ingredientNameList, page, size, sort);
    }

    @PostMapping("/data")
    public List<RecipeDTO> findAllByData(@Valid @RequestBody RecipeDataDTO recipeDataDTO,
                                         @RequestParam("page") int page,
                                         @RequestParam("size") int size,
                                         @RequestParam("sort") String sort) {
        return recipeService.findAllByData(recipeDataDTO, page, size, sort);
    }

    @PostMapping(value = "/addPhoto4Step/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CookingStepRecipeDTO createPhoto4Step(@PathVariable("id") @ValidUUID UUID id, @RequestParam MultipartFile file)
            throws IOException {
        return cookingStepsService.savePhoto(id, file);
    }

    @PostMapping(value = "/addPhoto4Recipe/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public RecipeDTO createPhoto4Recipe(@PathVariable("id") @ValidUUID UUID id, @RequestParam MultipartFile file)
            throws IOException {
        return recipeService.savePhoto(id, file);
    }

    @GetMapping("/authorName/id/{id}")
    public UserDTO getAuthorName(@PathVariable("id") @NotBlank String authorId) {
        return recipeService.getAuthorName(authorId);
    }

    @GetMapping("/countAllRecipesInCategory/{categoryName}")
    public int getCountAllRecipesInCategory(@PathVariable("categoryName") String categoryName) {
        return recipeService.getCountAllRecipesInCategory(categoryName);
    }

    @GetMapping("/countAllOwnRecipes/{authorId}")
    public int getCountAllOwnRecipes(@PathVariable("authorId") UUID authorId) {
        return recipeService.getCountAllOwnRecipes(authorId);
    }

    @GetMapping("/countAllRecipesByAuthorName/{authorName}")
    public int getCountAllRecipesByAuthorName(@PathVariable("authorName") String authorName) {
        return recipeService.getCountAllRecipesByAuthorName(authorName);
    }

    @GetMapping("/countAllRecipesByName/{name}")
    public int getCountAllRecipesByName(@PathVariable("name") String name) {
        return recipeService.getCountAllRecipesByName(name);
    }

    @GetMapping("/countAllRecipesByIngredient")
    public int getCountAllRecipesByIngredient() {
        return recipeService.getCountAllRecipesByIngredient();
    }

    @GetMapping("/countAllRecipesByData")
    public int getCountAllRecipesByData() {
        return recipeService.getCountAllRecipesByData();
    }

}
