package com.samsolutions.recipes.IT.repository;

import com.samsolutions.recipes.BaseTest;
import com.samsolutions.recipes.model.Enum.Type;
import com.samsolutions.recipes.model.IngredientEntity;
import com.samsolutions.recipes.repository.IngredientRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;

public class IngredientRepositoryTestIT extends BaseTest {
    @MockBean
    private TestEntityManager entityManager;

    @MockBean
    private IngredientRepository ingredientRepository;

    @Before
    public void setUp() {
        IngredientEntity ingredientEntity = new IngredientEntity();
        ingredientEntity.setType(Type.ALCOHOL);
        ingredientEntity.setName("test");
        ingredientEntity.setDescription("test");
        ingredientEntity.setCalories(100);

        Mockito.when(ingredientRepository.getByName(ingredientEntity.getName()))
                .thenReturn(ingredientEntity);
    }

    @Test
    public void shouldReturnOneIngredient() {
        //given
        IngredientEntity ingredientEntity = new IngredientEntity();
        ingredientEntity.setType(Type.ALCOHOL);
        ingredientEntity.setName("test");
        ingredientEntity.setDescription("test");
        ingredientEntity.setCalories(100);

        entityManager.persistAndFlush(ingredientEntity);
        //when
        IngredientEntity found = ingredientRepository.getByName(ingredientEntity.getName());

        //then
        assertThat(found.getId()).isEqualTo(ingredientEntity.getId());
    }
}
