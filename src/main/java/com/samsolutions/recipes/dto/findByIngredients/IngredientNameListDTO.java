package com.samsolutions.recipes.dto.findByIngredients;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author kaminskiy.alexey
 * @since 2019.12
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IngredientNameListDTO {
    private List<IngredientNameDTO> ingredientNameDTOList;
}
