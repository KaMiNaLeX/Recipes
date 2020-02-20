package com.samsolutions.recipes.dto.findByData;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDataDTO {
    private List<CookingDifficultyDTO> cookingDifficultyDTOList;
    private int cookingTime;
    @JsonProperty
    private boolean includeMeat;
    private String category;
}
