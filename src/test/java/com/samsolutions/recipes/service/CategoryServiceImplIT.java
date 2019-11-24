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
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author kaminskiy.alexey
 * @since 2019.11
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryServiceImplIT {

    @Autowired
    private CategoryService categoryService;

    @MockBean
    private CategoryRepository categoryRepository;


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

    @Test
    public void shouldAddOneCategory() {
        CategoryEntity breakfast = new CategoryEntity();
        breakfast.setName("Breakfast");
        breakfast.setDescription("Dishes for breakfast");
        breakfast.setTag("Healthy food,breakfast");
        categoryRepository.save(breakfast);
        when(categoryRepository.getByName(breakfast.getName())).thenReturn(breakfast);
        CategoryEntity found = categoryRepository.getByName(breakfast.getName());

        assertThat(found)
                .isEqualTo(breakfast);
    }

    @Test
    public void shouldUpdateCategory() {
        CategoryEntity breakfast = new CategoryEntity();
        breakfast.setName("Breakfast");
        breakfast.setDescription("Dishes for breakfast");
        breakfast.setTag("Healthy food,breakfast");
        categoryRepository.save(breakfast);

        CategoryEntity updateCategory = categoryRepository.getByName("Breakfast");
        updateCategory.setName(" Update Breakfast");
        updateCategory.setDescription("Update");
        updateCategory.setTag("Update");
        categoryRepository.save(updateCategory);

        assertThat(updateCategory).isNotEqualTo(breakfast);
    }

    //todo: need to fix
    @Test
    public void shouldRemoveCategory() {
        CategoryEntity breakfast = new CategoryEntity();
        breakfast.setName("Breakfast");
        breakfast.setDescription("Dishes for breakfast");
        breakfast.setTag("Healthy food,breakfast");
        categoryRepository.save(breakfast);
        CategoryEntity deleteEntity = categoryRepository.getByName(breakfast.getName());
        categoryRepository.delete(deleteEntity);
        CategoryEntity found = categoryRepository.getByName("Breakfast");
        assertThat(found).isNull();
    }

    @Test
    public void shouldReturnCategoryList() {
        CategoryEntity breakfast = new CategoryEntity("Breakfast",
                "Dishes for breakfast",
                "Healthy food,breakfast");

        CategoryEntity dinner = new CategoryEntity("Dinner",
                "Dishes for dinner",
                "Healthy food,dinner");
        categoryRepository.save(breakfast);
        categoryRepository.save(dinner);
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(breakfast, dinner));
        List<CategoryEntity> list = categoryRepository.findAll();

        assertThat(list).hasSize(2);
    }


    @TestConfiguration
    static class CategoryServiceImplTestContextConfiguration {

        @Bean
        public CategoryService categoryService() {
            return new com.samsolutions.recipes.service.impl.CategoryServiceImpl();
        }
    }

}
