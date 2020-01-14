package com.samsolutions.recipes.unit.service;

import com.samsolutions.recipes.BaseTest;
import com.samsolutions.recipes.dto.CategoryDTO;
import com.samsolutions.recipes.model.CategoryEntity;
import com.samsolutions.recipes.repository.CategoryRepository;
import com.samsolutions.recipes.service.CategoryService;
import org.junit.Test;
import org.mockito.Mock;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryServiceTest extends BaseTest {

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    CategoryService categoryService;

    @Test
    public void getByCategoryName() {
        String name = "test";
        CategoryDTO categoryDTO = createCategoryDTO();
        categoryService.createCategory(categoryDTO);
        CategoryEntity found = categoryRepository.getByName(name);
        CategoryDTO categoryDTO2 = categoryService.getByName(name);
        assertThat(categoryDTO2.getName()).isEqualTo(found.getName());

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
}
