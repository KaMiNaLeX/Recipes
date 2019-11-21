package com.samsolutions.recipes.controller;

import com.samsolutions.recipes.model.CookingStepsEntity;
import com.samsolutions.recipes.service.CookingStepsService;
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
@RequestMapping("/api/step")
public class CookingStepsRestController {
    @Autowired
    CookingStepsService cookingStepsService;

    @PostMapping("/create")
    public CookingStepsEntity createStep(@RequestBody CookingStepsEntity step) {
        return cookingStepsService.createStep(step);
    }

    @GetMapping("/")
    public List<CookingStepsEntity> findAll() {
        return cookingStepsService.findAll();
    }

    @GetMapping("/{id}")
    public CookingStepsEntity getById(@PathVariable("id") UUID uuid) {
        return cookingStepsService.getById(uuid);
    }

    @DeleteMapping("/delete/{id}")
    public void removeById(@PathVariable("id") UUID uuid) {
        cookingStepsService.removeStepById(uuid);
    }

    @PutMapping("update/{id}")
    public CookingStepsEntity updateStep(@PathVariable("id") UUID uuid, CookingStepsEntity step) {
        return cookingStepsService.updateStep(uuid, step);
    }
}
