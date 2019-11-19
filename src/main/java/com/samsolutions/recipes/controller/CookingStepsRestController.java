package com.samsolutions.recipes.controller;

import com.samsolutions.recipes.model.CookingStepsEntity;
import com.samsolutions.recipes.service.CookingStepsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author kaminskiy.alexey
 * @since 2019.11
 */
@RestController
@RequestMapping("/step")
public class CookingStepsRestController {
    @Autowired
    CookingStepsService cookingStepsService;

    @PostMapping("/create")
    public CookingStepsEntity createStep(@RequestBody CookingStepsEntity step) {
        return cookingStepsService.createStep(step);
    }
}
