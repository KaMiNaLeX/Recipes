package com.samsolutions.recipes.controller;

import com.samsolutions.recipes.dto.IngredientDTO;
import com.samsolutions.recipes.model.Enum.Type;
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
public class IngredientRestController {

    @Autowired
    private IngredientService ingredientService;

    @GetMapping("/")
    public List<IngredientDTO> findAll() {
        return ingredientService.findAll();
    }

    @GetMapping("/{id}")
    public IngredientDTO getById(@PathVariable("id") UUID uuid) {
        return ingredientService.getById(uuid);
    }

    @GetMapping("/type/{type}")
    public List<IngredientDTO> findAllByType(@PathVariable("type") Type type) {
        return ingredientService.findByType(type);
    }

    @PostMapping("/create")
    public IngredientDTO createIngredient(@RequestBody IngredientDTO ingredient) {
        return ingredientService.createIngredient(ingredient);
    }

    @PutMapping("/update/{id}")
    public IngredientDTO updateIngredient(@PathVariable("id") UUID uuid, @RequestBody IngredientDTO ingredient) {
        return ingredientService.updateIngredient(uuid, ingredient);
    }

    @DeleteMapping("/delete/{id}")
    public void removeById(@PathVariable("id") UUID uuid) {
        ingredientService.removeById(uuid);
    }
}
