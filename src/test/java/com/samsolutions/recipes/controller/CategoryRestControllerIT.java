package com.samsolutions.recipes.controller;

import com.samsolutions.recipes.model.CategoryEntity;
import com.samsolutions.recipes.service.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author kaminskiy.alexey
 * @since 2019.11
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryRestControllerIT {

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void shouldReturnListOfCategories() throws Exception {
        CategoryEntity breakfast = new CategoryEntity("Breakfast",
                "Dishes for breakfast",
                "Healthy food,breakfast");

        CategoryEntity dinner = new CategoryEntity("Dinner",
                "Dishes for dinner",
                "Healthy food,dinner");

        when(categoryService.findAll()).thenReturn(Arrays.asList(breakfast, dinner));
        ResponseEntity<CategoryEntity[]> categories = testRestTemplate
                .withBasicAuth("kamina", "kamina")
                .getForEntity("/api/category/", CategoryEntity[].class);
        assertThat(categories.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(categories.getBody()).hasSize(2);
    }

    //todo: need to fix
    @Test
    public void shouldReturnOneCategoryById() throws Exception {
        CategoryEntity breakfast = new CategoryEntity();
        breakfast.setName("Breakfast");
        breakfast.setDescription("Dishes for breakfast");
        breakfast.setTag("Healthy food,breakfast");
        when(categoryService.getById(breakfast.getId())).thenReturn(breakfast);
        ResponseEntity<CategoryEntity> category = testRestTemplate
                .withBasicAuth("kamina", "kamina")
                .getForEntity("/api/category/id/{id}", CategoryEntity.class,
                        categoryService.getById(breakfast.getId()));
        assertThat(category.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(category.getBody()).isEqualTo(breakfast);
    }

    @Test
    public void shouldAddCategory() {
        CategoryEntity breakfast = new CategoryEntity();
        breakfast.setName("Breakfast");
        breakfast.setDescription("Dishes for breakfast");
        breakfast.setTag("Healthy food,breakfast");
        ResponseEntity<CategoryEntity> category = testRestTemplate
                .withBasicAuth("kamina", "kamina")
                .postForEntity("/api/category/create", breakfast, CategoryEntity.class);
        assertThat(category.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
