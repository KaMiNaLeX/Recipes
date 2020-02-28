package com.samsolutions.recipes.dto.createRecipe;

import com.samsolutions.recipes.model.Enum.Unit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author kaminskiy.alexey
 * @since 2019.12
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IngredientRecipeDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String nameRu;
    private float amount;
    private String note;
    private String noteRu;
    private Unit unit;
    private Unit unitRu;
}
