package com.samsolutions.recipes.dto;

import com.samsolutions.recipes.service.validation.ValidUUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
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
public class CookingStepDTO {

    private UUID id;
    @NotBlank
    private String description;
    @NotBlank
    private String descriptionRu;
    private boolean active;
    private String imgSource;
    @ValidUUID
    private UUID recipeId;
}
