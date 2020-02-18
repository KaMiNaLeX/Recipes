package com.samsolutions.recipes.dto.createRecipe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author kaminskiy.alexey
 * @since 2019.12
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CookingStepRecipeDTO {

    private UUID id;
    @NotNull
    private int number;
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    private boolean active;
    private String imgSource;
}
