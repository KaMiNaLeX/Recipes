package com.samsolutions.recipes.controller;

import com.samsolutions.recipes.dto.CategoryDTO;
import com.samsolutions.recipes.dto.createRecipe.CategoryRecipeDTO;
import com.samsolutions.recipes.service.CategoryService;
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
 * Category RestController to manage categories.
 *
 * @author kaminskiy.alexey
 * @since 2019.11
 */
@RestController
@RequestMapping("/api/category")
public class CategoryRestController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public List<CategoryDTO> findAll() {
        return categoryService.findAll();
    }

    @GetMapping("/id/{id}")
    public CategoryDTO getById(@PathVariable("id") UUID uuid) {
        return categoryService.getById(uuid);
    }

    @DeleteMapping("/delete/{id}")
    public void removeById(@PathVariable("id") UUID uuid) {
        categoryService.removeById(uuid);
    }

    @PostMapping("/create")
    public CategoryDTO createCategory(@RequestBody CategoryDTO categoryDTO) {
        return categoryService.createCategory(categoryDTO);
    }

    @PutMapping("/update/{id}")
    public CategoryDTO updateCategory(@PathVariable("id") UUID uuid, @RequestBody CategoryDTO categoryDTO) {
        return categoryService.updateCategory(uuid, categoryDTO);
    }

    @GetMapping("/findAll")
    public List<CategoryRecipeDTO> findAllCategoriesDTO() {
        return categoryService.findAllCategoriesDTO();
    }

}
