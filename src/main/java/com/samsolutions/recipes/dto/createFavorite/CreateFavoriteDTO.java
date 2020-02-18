package com.samsolutions.recipes.dto.createFavorite;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author kaminskiy.alexey
 * @since 2020.01
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateFavoriteDTO {
    @NotNull
    private UUID userId;
    @NotNull
    private UUID recipeId;
}
