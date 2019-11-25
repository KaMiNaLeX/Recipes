package com.samsolutions.recipes.controller;

import com.samsolutions.recipes.BaseTest;
import com.samsolutions.recipes.model.CategoryEntity;
import com.samsolutions.recipes.service.CategoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;
import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author kaminskiy.alexey
 * @since 2019.11
 */
public class CategoryRestControllerIT extends BaseTest {

    private static final String HTTP_BASE = "http://localhost";

    @LocalServerPort
    private int port;

    private URL base;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Before
    public void setUp() throws Exception {
        this.base = new URL(HTTP_BASE + ":" + port + "/api/category");
    }

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
                .getForEntity("/", CategoryEntity[].class);
        assertThat(categories.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(categories.getBody()).hasSize(2);
    }

    //todo: need to fix
    /*
    @Test
    public void shouldReturnOneCategoryById() throws Exception {
        CategoryEntity breakfast = new CategoryEntity();
        breakfast.setName("Breakfast");
        breakfast.setDescription("Dishes for breakfast");
        breakfast.setTag("Healthy food,breakfast");
        ResponseEntity<CategoryEntity> createCategory = testRestTemplate
                .withBasicAuth("kamina", "kamina")
                .postForEntity("/api/category/create", breakfast, CategoryEntity.class);
        assertThat(createCategory.getStatusCode()).isEqualTo(HttpStatus.OK);

        when(categoryService.getById(breakfast.getId())).thenReturn(breakfast);
        ResponseEntity<CategoryEntity> category = testRestTemplate
                .withBasicAuth("kamina", "kamina")
                .getForEntity("/api/category/id/{id}", CategoryEntity.class,
                        categoryService.getById(createCategory.getBody().getId()));
        assertThat(category.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(category.getBody()).isEqualTo(breakfast);
    }

     */

    @Test
    public void shouldAddCategory() {
        CategoryEntity breakfast = new CategoryEntity();
        breakfast.setName("Breakfast");
        breakfast.setDescription("Dishes for breakfast");
        breakfast.setTag("Healthy food,breakfast");
        ResponseEntity<CategoryEntity> category = testRestTemplate
                .withBasicAuth("kamina", "kamina")
                .postForEntity("/create", breakfast, CategoryEntity.class);
        assertThat(category.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
