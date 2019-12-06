package com.samsolutions.recipes.controller;

import com.samsolutions.recipes.model.RecipeEntity;
import com.samsolutions.recipes.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/")
    public List<RecipeEntity> findAll() {
        return recipeService.findAll();
    }

    @PostMapping("/create")
    public RecipeEntity create(@RequestBody RecipeEntity recipeEntity) {
        return recipeService.create(recipeEntity);
    }

    @PutMapping("/update/{id}")
    public RecipeEntity update(@PathVariable("id") UUID uuid, RecipeEntity recipeEntity) {
        return recipeService.update(uuid, recipeEntity);
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
    public List<RecipeEntity> findAllByCategoryName(@PathVariable("name") String categoryName) {
        return recipeService.getByCategoryName(categoryName);
    }
}
