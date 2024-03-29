package com.samsolutions.recipes.controller;

import com.samsolutions.recipes.dto.CookingStepDTO;
import com.samsolutions.recipes.exception.CustomGlobalExceptionHandler;
import com.samsolutions.recipes.model.CookingStepsEntity;
import com.samsolutions.recipes.service.CookingStepsService;
import com.samsolutions.recipes.service.validation.ValidUUID;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @author kaminskiy.alexey
 * @since 2019.11
 */
@Log4j2
@Validated
@RestController
@RequestMapping("/api/step")
public class CookingStepsRestController extends CustomGlobalExceptionHandler {
    @Autowired
    private CookingStepsService cookingStepsService;

    @PostMapping("/create")
    public CookingStepDTO createStepDTO(@RequestBody CookingStepDTO step) throws IOException {
        return cookingStepsService.createStepDTO(step);
    }

    @GetMapping("/")
    public List<CookingStepsEntity> findAll() {
        return cookingStepsService.findAll();
    }

    @GetMapping("/findAll")
    public List<CookingStepDTO> findAllDto() {
        return cookingStepsService.findAllDto();
    }

    @GetMapping("/{id}")
    public CookingStepsEntity getById(@PathVariable("id") @ValidUUID UUID uuid) {
        return cookingStepsService.getById(uuid);
    }

    @DeleteMapping("/delete/{id}")
    public void removeById(@PathVariable("id") @ValidUUID UUID uuid) {
        cookingStepsService.removeStepById(uuid);
        log.info("Remove step " + uuid + " is successful");
    }
}
