package com.samsolutions.recipes.dto.createFavorite;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private UUID userId;
    private UUID recipeId;
}
