package com.samsolutions.recipes.controller;

import com.samsolutions.recipes.model.IngredientEntity;
import com.samsolutions.recipes.service.IngredientService;
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
 * @since 2019.11
 */
@RestController
@RequestMapping("/api/ingredient")
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    @GetMapping("/")
    public List<IngredientEntity> findAll() {
        return ingredientService.findAll();
    }

    @GetMapping("/{id}")
    public IngredientEntity getById(@PathVariable("id") UUID uuid) {
        return ingredientService.getById(uuid);
    }

    @PostMapping("/create")
    public IngredientEntity createIngredient(@RequestBody IngredientEntity ingredient) {
        return ingredientService.createIngredient(ingredient);
    }

    @PutMapping("/update/{id}")
    public IngredientEntity updateIngredient(@PathVariable("id") UUID uuid, IngredientEntity ingredient) {
        return ingredientService.updateIngredient(uuid, ingredient);
    }

    @DeleteMapping("/delete/{id}")
    public void removeById(@PathVariable("id") UUID uuid) {
        ingredientService.removeById(uuid);
    }
}
