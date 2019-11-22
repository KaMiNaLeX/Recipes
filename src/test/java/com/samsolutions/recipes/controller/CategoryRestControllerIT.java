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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author kaminskiy.alexey
 * @since 2019.11
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "spring.security.user.password=kamina#")
public class CategoryRestControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    CategoryService categoryService;

    @Test
    public void shouldReturnListOfCategories() throws Exception {
        CategoryEntity breakfast = new CategoryEntity();
        breakfast.setName("Breakfast");
        breakfast.setDescription("Dishes for breakfast");
        breakfast.setTag("Healthy food,breakfast");

        when(categoryService.findAll()).thenReturn(Collections.singletonList(breakfast)
        );
        ResponseEntity<CategoryEntity[]> categories = testRestTemplate
                .withBasicAuth("kamina", "kamina#")
                .getForEntity("/api/category/", CategoryEntity[].class);
        assertThat(categories.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(categories.getBody()).hasSize(1);
    }
}
