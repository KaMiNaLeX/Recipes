package com.samsolutions.recipes.service;

import com.samsolutions.recipes.model.CategoryEntity;
import com.samsolutions.recipes.repository.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author kaminskiy.alexey
 * @since 2019.11
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryServiceImplTest {

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
    public void shouldReturnOneCategoryByName() {
        String name = "Breakfast";
        CategoryEntity found = categoryService.getByName(name);

        assertThat(found.getName())
                .isEqualTo(name);
    }
    //todo: need to fix
    @Test
    public void shouldAddOneCategory() {
        CategoryEntity breakfast = new CategoryEntity();
        breakfast.setName("Breakfast");
        breakfast.setDescription("Dishes for breakfast");
        breakfast.setTag("Healthy food,breakfast");
        categoryRepository.save(breakfast);
        CategoryEntity found = categoryRepository.getByName(breakfast.getName());

        assertThat(found)
                .isEqualTo(breakfast);
    }

    @TestConfiguration
    static class CategoryServiceImplTestContextConfiguration {

        @Bean
        public CategoryService categoryService() {
            return new com.samsolutions.recipes.service.impl.CategoryServiceImpl();
        }
    }

}
