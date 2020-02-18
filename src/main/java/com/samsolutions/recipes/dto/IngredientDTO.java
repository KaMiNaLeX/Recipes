package com.samsolutions.recipes.dto;

import com.samsolutions.recipes.model.Enum.Type;
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
public class IngredientDTO {

    private UUID id;
    @NotEmpty
    private String name;
    private String description;
    @NotNull
    private double calories;
    private Type type;
}
