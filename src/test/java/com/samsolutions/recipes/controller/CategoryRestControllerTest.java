package com.samsolutions.recipes.controller;

import com.samsolutions.recipes.model.CategoryEntity;
import com.samsolutions.recipes.service.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author kaminskiy.alexey
 * @since 2019.11
 */
@RunWith(SpringRunner.class)
@WebMvcTest(CategoryRestControllerTest.class)
public class CategoryRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CategoryService categoryService;

    @Test
    public void shouldReturnListOfCategories() throws Exception {
        CategoryEntity breakfast = new CategoryEntity();
        breakfast.setName("Breakfast");
        breakfast.setDescription("Dishes for breakfast");
        breakfast.setTag("Healthy food,breakfast");
        when(categoryService.findAll()).thenReturn(Collections.singletonList(breakfast));
        mockMvc.perform(get("/api/category/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[*].name",
                        containsInAnyOrder("Breakfast")));

    }
}
