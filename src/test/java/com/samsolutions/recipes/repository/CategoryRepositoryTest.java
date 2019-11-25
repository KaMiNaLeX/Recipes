package com.samsolutions.recipes.repository;

import com.samsolutions.recipes.BaseTest;
import com.samsolutions.recipes.model.CategoryEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author kaminskiy.alexey
 * @since 2019.11
 */
public class CategoryRepositoryTest extends BaseTest {

    @MockBean
    private TestEntityManager entityManager;

    @MockBean
    private CategoryRepository categoryRepository;

    //todo: need to fix

    @Test
    public void shouldReturnOneCategory() {
        //given
        CategoryEntity breakfast = new CategoryEntity();
        breakfast.setName("Breakfast");
        breakfast.setDescription("Dishes for breakfast");
        breakfast.setTag("Healthy food,breakfast");

        entityManager.persistAndFlush(breakfast);
        //when
        CategoryEntity found = categoryRepository.getByName(breakfast.getName());

        //then
        assertThat(found.getId())
                .isEqualTo(breakfast.getId());
    }

}
