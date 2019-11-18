package com.samsolutions.recipes.controller;

import com.samsolutions.recipes.model.CategoryEntity;
import com.samsolutions.recipes.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Category RestController to manage categories.
 *
 * @author kaminskiy.alexey
 * @since 2019.11
 */
@RestController
@RequestMapping("/category")
public class CategoryRestController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/")
    public List<CategoryEntity> findAll() {
        return categoryService.findAll();
    }

    @GetMapping("/id/{id}")
    public CategoryEntity getById(@PathVariable("id") UUID uuid) {
        return categoryService.getById(uuid);
    }

    @DeleteMapping("/delete/{id}")
    public void removeById(@PathVariable("id") UUID uuid) {
        categoryService.removeById(uuid);
    }

    @PostMapping("/create")
    public CategoryEntity createCategory(@RequestBody CategoryEntity categoryEntity) {
        return categoryService.createCategory(categoryEntity);
    }

    @PutMapping("/{id}")
    public CategoryEntity updateCategory(@PathVariable("id") UUID uuid, @RequestBody CategoryEntity categoryEntity) {
        return categoryService.updateCategory(uuid, categoryEntity);
    }
}
