package com.samsolutions.recipes.unit.service;

import com.samsolutions.recipes.BaseTest;
import com.samsolutions.recipes.dto.CategoryDTO;
import com.samsolutions.recipes.model.CategoryEntity;
import com.samsolutions.recipes.repository.CategoryRepository;
import com.samsolutions.recipes.service.CategoryService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryServiceTest extends BaseTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryService categoryService;

    @Test
    public void getByCategoryName() {
        CategoryDTO categoryDTO = createCategoryDTO();
        categoryService.createCategory(categoryDTO);
        CategoryEntity found = categoryRepository.getById(categoryDTO.getId());
        CategoryDTO categoryDTO2 = categoryService.getById(categoryDTO.getId());
        assertThat(categoryDTO2.getName()).isEqualTo(found.getName());
    }

    @Test
    public void addCategory() {
        CategoryEntity breakfast = createCategory();
        categoryRepository.save(breakfast);
        CategoryDTO found = categoryService.getByName(breakfast.getName());

        assertThat(found.getName())
                .isEqualTo(breakfast.getName());
    }

    @Test
    public void shouldUpdateCategory() {
        CategoryEntity breakfast = createCategory();
        categoryRepository.save(breakfast);
        CategoryDTO categoryDTO = createCategoryDTO();
        categoryService.updateCategory(breakfast.getId(), categoryDTO);
        assertThat(categoryService.getById(breakfast.getId()).getName()).isEqualTo("test");
    }

    @Test
    public void shouldDeleteCategory() {
        CategoryEntity breakfast = createCategory();
        categoryRepository.save(breakfast);
        assertThat(categoryService.getById(breakfast.getId()).getName()).isEqualTo("Breakfast");
        categoryRepository.delete(breakfast);
        assertThat(categoryService.findAll().isEmpty());
    }

    private CategoryDTO createCategoryDTO() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setImgSource(null);
        categoryDTO.setTag("test");
        categoryDTO.setDescription("test");
        categoryDTO.setName("test");
        //categoryDTO.setId(UUID.randomUUID());
        return categoryDTO;
    }

    public CategoryEntity createCategory() {
        CategoryEntity breakfast = new CategoryEntity();
        breakfast.setName("Breakfast");
        breakfast.setDescription("Dishes for breakfast");
        breakfast.setTag("Healthy food,breakfast");
        breakfast.setImgSource(null);
        return breakfast;
    }
}
