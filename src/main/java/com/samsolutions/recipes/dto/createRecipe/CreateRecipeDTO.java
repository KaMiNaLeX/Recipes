package com.samsolutions.recipes.dto.createRecipe;

import com.samsolutions.recipes.model.Enum.CookingDifficulty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author kaminskiy.alexey
 * @since 2019.12
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateRecipeDTO {
    private UUID id;
    private String name;
    private CookingDifficulty cookingDifficulty;
    private int cookingTime;
    private int positiveVotes;
    private int negativeVotes;
    private Date lastModified;
    private UUID authorId;
    private String categoryName;
    private List<IngredientRecipeDTO> ingredientRecipeDTOList;
    private List<CookingStepRecipeDTO> cookingStepRecipeDTOList;
}
