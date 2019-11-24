package com.samsolutions.recipes.repository;

import com.samsolutions.recipes.model.CategoryEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author kaminskiy.alexey
 * @since 2019.11
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class CategoryRepositoryTest {

    @MockBean
    private CategoryRepository categoryRepository;

    @Test
    public void shouldReturnOneCategory() {
        //given
        CategoryEntity breakfast = new CategoryEntity();
        breakfast.setName("Breakfast");
        breakfast.setDescription("Dishes for breakfast");
        breakfast.setTag("Healthy food,breakfast");

        categoryRepository.save(breakfast);
        //when
        CategoryEntity found = categoryRepository.getByName(breakfast.getName());

        //then
        assertThat(found.getId())
                .isEqualTo(breakfast.getId());
    }
}
