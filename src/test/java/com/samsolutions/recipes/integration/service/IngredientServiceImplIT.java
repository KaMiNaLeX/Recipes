package com.samsolutions.recipes.integration.service;

import com.samsolutions.recipes.BaseTest;
import com.samsolutions.recipes.model.Enum.Type;
import com.samsolutions.recipes.model.IngredientEntity;
import com.samsolutions.recipes.repository.IngredientRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class IngredientServiceImplIT extends BaseTest {

    @MockBean
    private IngredientRepository ingredientRepository;

    @Before
    public void setUp() {
        IngredientEntity ingredientEntity = createIngredient();
        Mockito.when(ingredientRepository.getByName(ingredientEntity.getName()))
                .thenReturn(ingredientEntity);
    }

    @Test
    public void shouldReturnOneIngredientByName() {
        String name = "test";
        IngredientEntity found = ingredientRepository.getByName(name);
        assertThat(found.getName())
                .isEqualTo(name);
    }

    @Test
    public void shouldAddOneIngredient() {
        IngredientEntity ingredientEntity = createIngredient();
        ingredientRepository.save(ingredientEntity);
        when(ingredientRepository.getByName(ingredientEntity.getName())).thenReturn(ingredientEntity);
        IngredientEntity found = ingredientRepository.getByName(ingredientEntity.getName());

        assertThat(found)
                .isEqualTo(ingredientEntity);
    }

    @Test
    public void shouldUpdateIngredient() {
        IngredientEntity ingredientEntity = createIngredient();
        ingredientRepository.save(ingredientEntity);

        IngredientEntity updateIngredient = ingredientRepository.getByName("test");
        updateIngredient.setName(" Update test");
        updateIngredient.setDescription("update test");
        updateIngredient.setCalories(120);
        updateIngredient.setType(Type.ALCOHOL);
        ingredientRepository.save(updateIngredient);

        assertThat(updateIngredient).isNotEqualTo(ingredientEntity);
    }

    public IngredientEntity createIngredient() {
        IngredientEntity ingredientEntity = new IngredientEntity();
        ingredientEntity.setType(Type.ALCOHOL);
        ingredientEntity.setName("test");
        ingredientEntity.setDescription("test");
        ingredientEntity.setCalories(100);
        return ingredientEntity;
    }
}
