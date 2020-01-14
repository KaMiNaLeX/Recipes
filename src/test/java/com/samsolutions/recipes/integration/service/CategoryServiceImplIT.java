package com.samsolutions.recipes.integration.service;

import com.samsolutions.recipes.BaseTest;
import com.samsolutions.recipes.model.CategoryEntity;
import com.samsolutions.recipes.repository.CategoryRepository;
import com.samsolutions.recipes.service.CategoryService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author kaminskiy.alexey
 * @since 2019.11
 */
public class CategoryServiceImplIT extends BaseTest {

    @MockBean
    private CategoryRepository categoryRepository;

    @Before
    public void setUp() {
        CategoryEntity breakfast = createCategory();

        Mockito.when(categoryRepository.getByName(breakfast.getName()))
                .thenReturn(breakfast);
    }

    @Test
    public void shouldReturnOneCategoryByName() {
        String name = "Breakfast";
        CategoryEntity found = categoryRepository.getByName(name);

        assertThat(found.getName())
                .isEqualTo(name);
    }

    @Test
    public void shouldAddOneCategory() {
        CategoryEntity breakfast = createCategory();
        categoryRepository.save(breakfast);
        when(categoryRepository.getByName(breakfast.getName())).thenReturn(breakfast);
        CategoryEntity found = categoryRepository.getByName(breakfast.getName());

        assertThat(found)
                .isEqualTo(breakfast);
    }

    @Test
    public void shouldUpdateCategory() {
        CategoryEntity breakfast = createCategory();
        categoryRepository.save(breakfast);

        CategoryEntity updateCategory = categoryRepository.getByName("Breakfast");
        updateCategory.setName(" Update Breakfast");
        updateCategory.setDescription("Update");
        updateCategory.setTag("Update");
        breakfast.setImgSource(null);
        categoryRepository.save(updateCategory);

        assertThat(updateCategory).isNotEqualTo(breakfast);
    }

    /*
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
    
     */

    public CategoryEntity createCategory() {
        CategoryEntity breakfast = new CategoryEntity();
        breakfast.setName("Breakfast");
        breakfast.setDescription("Dishes for breakfast");
        breakfast.setTag("Healthy food,breakfast");
        breakfast.setImgSource(null);
        return breakfast;
    }

    @TestConfiguration
    static class CategoryServiceImplTestContextConfiguration {

        @Bean
        public CategoryService categoryService() {
            return new com.samsolutions.recipes.service.impl.CategoryServiceImpl();
        }
    }

}
