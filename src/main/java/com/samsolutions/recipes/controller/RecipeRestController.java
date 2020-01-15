package com.samsolutions.recipes.controller;

import com.samsolutions.recipes.dto.RecipeDTO;
import com.samsolutions.recipes.dto.UserDTO;
import com.samsolutions.recipes.dto.createRecipe.CookingStepRecipeDTO;
import com.samsolutions.recipes.dto.createRecipe.CreateRecipeDTO;
import com.samsolutions.recipes.dto.findByData.RecipeDataDTO;
import com.samsolutions.recipes.dto.findByIngredients.IngredientNameListDTO;
import com.samsolutions.recipes.service.CookingStepsService;
import com.samsolutions.recipes.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
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

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @author kaminskiy.alexey
 * @since 2019.12
 */
@RestController
@RequestMapping("/api/recipe")
public class RecipeRestController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private CookingStepsService cookingStepsService;

    @GetMapping("/")
    public List<RecipeDTO> findAll() {
        return recipeService.findAll();
    }

    @PostMapping("/create")
    public RecipeDTO create(@RequestBody RecipeDTO recipeDTO) {
        return recipeService.create(recipeDTO);
    }

    @PutMapping("/update/{id}")
    public RecipeDTO update(@PathVariable("id") UUID uuid, @RequestBody RecipeDTO recipeDTO) {
        return recipeService.update(uuid, recipeDTO);
    }

    @PreAuthorize("hasAnyRole('ADMIN','AUTHOR')")
    @PutMapping("/updateRecipe/{id}")
    public CreateRecipeDTO updateRecipe(@PathVariable("id") UUID uuid, @RequestBody CreateRecipeDTO recipeDTO)
            throws IOException {
        return recipeService.updateRecipe(uuid, recipeDTO);
    }

    @DeleteMapping("/delete/{id}")
    public void removeById(@PathVariable("id") UUID uuid) {
        recipeService.removeById(uuid);
    }

    @PutMapping("/positive/{id}")
    public void positiveVote(@PathVariable("id") UUID uuid) {
        recipeService.positiveVote(uuid);
    }

    @PutMapping("/negative/{id}")
    public void negativeVote(@PathVariable("id") UUID uuid) {
        recipeService.negativeVote(uuid);
    }

    @GetMapping("/categoryName/{name}")
    public List<RecipeDTO> findAllByCategoryName(@PathVariable("name") String categoryName) {
        return recipeService.getByCategoryName(categoryName);
    }

    @PreAuthorize("hasAnyRole('ADMIN','AUTHOR')")
    @PostMapping("/createRecipe")
    public CreateRecipeDTO createRecipeDTO(@RequestBody CreateRecipeDTO createRecipeDTO) {
        try {
            return recipeService.createRecipeDTO(createRecipeDTO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GetMapping("/id/{id}")
    public CreateRecipeDTO getByRecipeId(@PathVariable("id") UUID uuid) {
        return recipeService.getByRecipeId(uuid);
    }

    @PreAuthorize("hasAnyRole('ADMIN','AUTHOR')")
    @GetMapping("/authorId/{id}")
    public List<CreateRecipeDTO> getByAuthorId(@PathVariable("id") UUID uuid) {
        return recipeService.getByAuthorId(uuid);
    }

    @GetMapping("/authorName/{name}")
    public List<RecipeDTO> getByAuthorName(@PathVariable("name") String name) {
        return recipeService.getByAuthorName(name);
    }

    @GetMapping("/name/{name}")
    public List<RecipeDTO> findAllByName(@PathVariable("name") String name) {
        return recipeService.findAllByName(name);
    }

    @GetMapping("/nameAndAuthor/{name}/{authorId}")
    public RecipeDTO getByNameAuthorId(@PathVariable("name") String name, @PathVariable("authorId") UUID uuid) {
        try {
            return recipeService.getByNameAuthorId(name, uuid);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @PostMapping("/ingredientName")
    public List<RecipeDTO> findAllByIngredientName(@RequestBody IngredientNameListDTO ingredientNameList) {
        return recipeService.findAllByIngredient(ingredientNameList);
    }

    @PostMapping("/data")
    public List<RecipeDTO> findAllByData(@RequestBody RecipeDataDTO recipeDataDTO) {
        return recipeService.findAllByData(recipeDataDTO);
    }

    @PostMapping(value = "/addPhoto4Step/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CookingStepRecipeDTO createPhoto4Step(@PathVariable("id") UUID id, @RequestParam MultipartFile file)
            throws IOException {
        return cookingStepsService.savePhoto(id, file);
    }

    @PostMapping(value = "/addPhoto4Recipe/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public RecipeDTO createPhoto4Recipe(@PathVariable("id") UUID id, @RequestParam MultipartFile file)
            throws IOException {
        return recipeService.savePhoto(id, file);
    }

    @GetMapping("/authorName/id/{id}")
    public UserDTO getAuthorName(@PathVariable("id") String authorId) {
        return recipeService.getAuthorName(authorId);
    }

}
