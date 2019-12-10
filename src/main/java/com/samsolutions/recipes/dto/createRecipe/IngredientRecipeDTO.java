package com.samsolutions.recipes.dto.createRecipe;

import com.samsolutions.recipes.model.Enum.Unit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author kaminskiy.alexey
 * @since 2019.12
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IngredientRecipeDTO {
    private String name;
    private float amount;
    private String note;
    private Unit unit;
}
