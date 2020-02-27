package com.samsolutions.recipes.dto.findByIngredients;

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
public class IngredientNameDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String nameRu;
}
