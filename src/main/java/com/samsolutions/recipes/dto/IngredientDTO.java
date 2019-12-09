package com.samsolutions.recipes.dto;

import com.samsolutions.recipes.model.Enum.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * @author kaminskiy.alexey
 * @since 2019.12
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDTO {

    private UUID id;
    private String name;
    private String description;
    private double calories;
    private Type type;
}
