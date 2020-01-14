package com.samsolutions.recipes.unit.service;

import com.samsolutions.recipes.BaseTest;
import com.samsolutions.recipes.dto.IngredientDTO;
import com.samsolutions.recipes.model.Enum.Type;
import com.samsolutions.recipes.model.IngredientEntity;
import com.samsolutions.recipes.repository.IngredientRepository;
import com.samsolutions.recipes.service.IngredientService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class IngredientServiceTest extends BaseTest {
    @Autowired
    private IngredientService ingredientService;
    @Autowired
    private IngredientRepository ingredientRepository;

    @Test
    public void getByIngredientId() {
        String name = "test";
        IngredientDTO ingredientDTO = createIngredientDTO();
        ingredientService.createIngredient(ingredientDTO);
        IngredientEntity found = ingredientRepository.getById(ingredientDTO.getId());
        IngredientDTO ingredientDTO1 = ingredientService.getById(ingredientDTO.getId());
        assertThat(ingredientDTO1.getId()).isEqualTo(found.getId());
    }

    @Test
    public void addIngredient() {
        IngredientEntity ingredientEntity = new IngredientEntity();
        ingredientEntity.setType(Type.ALCOHOL);
        ingredientEntity.setName("test2");
        ingredientEntity.setDescription("test2");
        ingredientEntity.setCalories(100);
        ingredientRepository.save(ingredientEntity);
        IngredientDTO found = ingredientService.getByName(ingredientEntity.getName());

        assertThat(found.getName())
                .isEqualTo(ingredientEntity.getName());
    }

    @Test
    public void shouldUpdateIngredient() {
        IngredientEntity ingredientEntity = createIngredient();
        ingredientRepository.save(ingredientEntity);
        IngredientDTO ingredientDTO = createIngredientDTO();
        ingredientService.updateIngredient(ingredientEntity.getId(), ingredientDTO);
        assertThat(ingredientService.getById(ingredientEntity.getId()).getName()).isEqualTo("test");
    }

    @Test
    public void shouldDeleteIngredient() {
        IngredientEntity ingredientEntity = createIngredient();
        ingredientRepository.save(ingredientEntity);
        assertThat(ingredientService.getById(ingredientEntity.getId()).getName()).isEqualTo("test");
        ingredientRepository.delete(ingredientEntity);
        assertThat(ingredientService.findAll().isEmpty());
    }

    public IngredientEntity createIngredient() {
        IngredientEntity ingredientEntity = new IngredientEntity();
        ingredientEntity.setType(Type.ALCOHOL);
        ingredientEntity.setName("test");
        ingredientEntity.setDescription("test");
        ingredientEntity.setCalories(100);
        return ingredientEntity;
    }

    private IngredientDTO createIngredientDTO() {
        IngredientDTO ingredientDTO = new IngredientDTO();
        ingredientDTO.setCalories(100);
        ingredientDTO.setDescription("test");
        ingredientDTO.setName("test");
        ingredientDTO.setType(Type.ALCOHOL);
        ingredientDTO.setId(UUID.randomUUID());
        return ingredientDTO;
    }
}
