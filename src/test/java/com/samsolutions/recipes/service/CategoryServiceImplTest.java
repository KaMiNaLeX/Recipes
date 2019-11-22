package com.samsolutions.recipes.service;

import com.samsolutions.recipes.model.CategoryEntity;
import com.samsolutions.recipes.repository.CategoryRepository;
import com.samsolutions.recipes.repository.CategoryRepositoryTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author kaminskiy.alexey
 * @since 2019.11
 */
@RunWith(SpringRunner.class)
public class CategoryServiceImplTest {

    @TestConfiguration
    static class CategoryServiceImplTestContextConfiguration {

        @Bean
        public CategoryService categoryService() {
            return new com.samsolutions.recipes.service.impl.CategoryServiceImpl();
        }
    }

    @Autowired
    CategoryService categoryService;

    @MockBean
    CategoryRepository categoryRepository;

    @Before
    public void setUp() {
        CategoryEntity breakfast = new CategoryEntity();
        breakfast.setName("Breakfast");
        breakfast.setDescription("Dishes for breakfast");
        breakfast.setTag("Healthy food,breakfast");

        Mockito.when(categoryRepository.getByName(breakfast.getName()))
                .thenReturn(breakfast);
    }

    @Test
    public void whenValidId_thenCategoryShouldBeFound() {
        UUID uuid = categoryService.getByName("Breakfast").getId();
        CategoryEntity found = categoryService.getById(uuid);

        assertThat(found.getId())
                .isEqualTo(uuid);
    }

}
