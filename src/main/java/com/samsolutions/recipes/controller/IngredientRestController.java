package com.samsolutions.recipes.controller;

import com.samsolutions.recipes.dto.IngredientDTO;
import com.samsolutions.recipes.exception.CustomGlobalExceptionHandler;
import com.samsolutions.recipes.model.Enum.Type;
import com.samsolutions.recipes.service.IngredientService;
import com.samsolutions.recipes.service.validation.ValidUUID;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
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

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

/**
 * @author kaminskiy.alexey
 * @since 2019.11
 */
@Log4j2
@Validated
@RestController
@RequestMapping("/api/ingredient")
public class IngredientRestController extends CustomGlobalExceptionHandler {

    @Autowired
    private IngredientService ingredientService;

    @GetMapping("/findAll")
    public List<IngredientDTO> findAll() {
        return ingredientService.findAll();
    }

    @GetMapping("/")
    public List<IngredientDTO> findAll(@RequestParam("page") int page, @RequestParam("size") @NotNull int size,
                                       @RequestParam("sort") @NotBlank String sort) {
        return ingredientService.findAll(page, size, sort);
    }

    @GetMapping("/{id}")
    public IngredientDTO getById(@PathVariable("id") @ValidUUID UUID uuid) {
        return ingredientService.getById(uuid);
    }

    @GetMapping("/type/{type}")
    public List<IngredientDTO> findAllByType(@PathVariable("type") Type type) {
        return ingredientService.findByType(type);
    }

    @GetMapping("/name/{name}")
    public IngredientDTO getByName(@PathVariable("name") String name) {
        return ingredientService.getByName(name);
    }

    @PostMapping("/create")
    public IngredientDTO createIngredient(@Valid @RequestBody IngredientDTO ingredient) {
        try {
            log.info("Create ingredient " + ingredient.getName() + " is successful");
            return ingredientService.createIngredient(ingredient);
        } catch (Exception e) {
            log.error("Create ingredient " + ingredient.getName() + " is failed", e.getCause());
            return null;
        }
    }

    @PutMapping("/update/{id}")
    public IngredientDTO updateIngredient(@PathVariable("id") @ValidUUID UUID uuid,
                                          @Valid @RequestBody IngredientDTO ingredient) {
        try {
            log.info("Update ingredient " + uuid + " is successful");
            return ingredientService.updateIngredient(uuid, ingredient);
        } catch (Exception e) {
            log.error("Update ingredient " + uuid + " is failed", e.getCause());
            return null;
        }
    }

    @DeleteMapping("/delete/{id}")
    public boolean removeById(@PathVariable("id") @ValidUUID UUID uuid) {
        return ingredientService.removeById(uuid);
    }
}
