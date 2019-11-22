package com.samsolutions.recipes.repository;

import com.samsolutions.recipes.model.CategoryEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author kaminskiy.alexey
 * @since 2019.11
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class CategoryRepositoryIT {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    public void whenFindById_ThenReturnCategory() {
        //given
        CategoryEntity breakfast = new CategoryEntity();
        breakfast.setName("Breakfast");
        breakfast.setDescription("Dishes for breakfast");
        breakfast.setTag("Healthy food,breakfast");

        testEntityManager.persist(breakfast);
        testEntityManager.flush();

        //when
        CategoryEntity found = categoryRepository.getById(breakfast.getId());

        //then
        assertThat(found.getId())
                .isEqualTo(breakfast.getId());
    }
}
