package com.samsolutions.recipes.controller;

import com.samsolutions.recipes.dto.CommentsDTO;
import com.samsolutions.recipes.dto.RecipeVotesDTO;
import com.samsolutions.recipes.exception.CustomGlobalExceptionHandler;
import com.samsolutions.recipes.model.RecipeVotesEntity;
import com.samsolutions.recipes.service.RecipeVotesService;
import com.samsolutions.recipes.service.validation.ValidUUID;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

/**
 * @author Kaminskiy Alexey
 * @since 2020.03
 **/
@Log4j2
@Validated
@RestController
@RequestMapping("/api/vote")
public class RecipeVotesController extends CustomGlobalExceptionHandler {

    @Autowired
    private RecipeVotesService recipeVotesService;

    @PostMapping("/create")
    public RecipeVotesDTO create(@Valid @RequestBody RecipeVotesDTO recipeVotesDTO) {
        try {
            return recipeVotesService.create(recipeVotesDTO);
        } catch (Exception e) {
            log.error("Create recipeVotes is failed", e.getCause());
            return null;
        }
    }

    @DeleteMapping("/delete/{id}")
    public void removeById(@PathVariable("id") @ValidUUID UUID uuid) {
        recipeVotesService.delete(uuid);
        log.info("Remove " + uuid + " recipeVotes is successful");
    }
}
