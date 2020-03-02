package com.samsolutions.recipes.controller;

import com.samsolutions.recipes.dto.CategoryDTO;
import com.samsolutions.recipes.dto.createRecipe.CategoryRecipeDTO;
import com.samsolutions.recipes.exception.CustomGlobalExceptionHandler;
import com.samsolutions.recipes.model.CategoryEntity;
import com.samsolutions.recipes.service.CategoryService;
import com.samsolutions.recipes.service.validation.ValidUUID;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Category RestController to manage categories.
 *
 * @author kaminskiy.alexey
 * @since 2019.11
 */
@Log4j2
@Validated
@RestController
@RequestMapping("/api/category")
public class CategoryRestController extends CustomGlobalExceptionHandler {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/findAllEntities")
    public List<CategoryEntity> findAllEntities() {
        return categoryService.findAllEntities();
    }

    @GetMapping("/categories")
    public List<CategoryDTO> findAll() {
        return categoryService.findAll();
    }

    @GetMapping("/name/{name}")
    public CategoryDTO getByName(@PathVariable("name") String name) {
        return categoryService.getByName(name);
    }

    @GetMapping("/")
    public List<CategoryDTO> findAll(@RequestParam("page") int page, @RequestParam("size") int size,
                                     @RequestParam("sort") String sort) {
        return categoryService.findAll(page, size, sort);
    }

    @GetMapping("/id/{id}")
    public CategoryDTO getById(@PathVariable("id") UUID uuid) {
        return categoryService.getById(uuid);
    }

    @DeleteMapping("/delete/{id}")
    public void removeById(@PathVariable("id") @ValidUUID UUID uuid) {
        categoryService.removeById(uuid);
        log.info("Remove " + uuid + " category is successful");
    }

    @PostMapping("/create")
    public CategoryDTO createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        try {
            log.info("Create category " + categoryDTO.getName() + " is successful");
            return categoryService.createCategory(categoryDTO);
        } catch (Exception e) {
            log.error("Create category " + categoryDTO.getName() + " is failed", e.getCause());
            return null;
        }
    }

    @PutMapping("/update/{id}")
    public CategoryDTO updateCategory(@PathVariable("id") @ValidUUID UUID uuid,
                                      @Valid @RequestBody CategoryDTO categoryDTO) {
        try {
            log.info("Update category " + uuid + "  is successful");
            return categoryService.updateCategory(uuid, categoryDTO);
        } catch (Exception e) {
            log.error("Update category " + uuid + " is failed", e.getCause());
            return null;
        }
    }

    @GetMapping("/findAll")
    public List<CategoryRecipeDTO> findAllCategoriesDTO() {
        return categoryService.findAllCategoriesDTO();
    }

    @PostMapping(value = "/addPhoto4Category/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CategoryDTO createPhoto4Category(@PathVariable("id") @ValidUUID UUID id, @RequestParam MultipartFile file)
            throws IOException {
        return categoryService.savePhoto(id, file);
    }
}
