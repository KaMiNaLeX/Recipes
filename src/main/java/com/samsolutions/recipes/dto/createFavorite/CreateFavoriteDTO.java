package com.samsolutions.recipes.dto.createFavorite;

import com.samsolutions.recipes.service.validation.ValidUUID;
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
    @ValidUUID
    private UUID userId;
    @ValidUUID
    private UUID recipeId;
}
