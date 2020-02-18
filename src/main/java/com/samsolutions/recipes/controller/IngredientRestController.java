package com.samsolutions.recipes.controller;

import com.samsolutions.recipes.dto.IngredientDTO;
import com.samsolutions.recipes.model.Enum.Type;
import com.samsolutions.recipes.service.IngredientService;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
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
        try {
            log.info("Create ingredient " + ingredient.getName() + " is successful");
            return ingredientService.createIngredient(ingredient);
        } catch (Exception e) {
            log.error("Create ingredient " + ingredient.getName() + " is failed", e.getCause());
            return null;
        }
    }

    @PutMapping("/update/{id}")
    public IngredientDTO updateIngredient(@PathVariable("id") UUID uuid, @RequestBody IngredientDTO ingredient) {
        try {
            log.info("Update ingredient " + uuid + " is successful");
            return ingredientService.updateIngredient(uuid, ingredient);
        } catch (Exception e) {
            log.error("Update ingredient " + uuid + " is failed", e.getCause());
            return null;
        }
    }

    @DeleteMapping("/delete/{id}")
    public void removeById(@PathVariable("id") UUID uuid) {
        ingredientService.removeById(uuid);
        log.info("Remove ingredient " + uuid + " is successful");
    }
}
