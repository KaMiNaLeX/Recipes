package com.samsolutions.recipes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsolutions.recipes.service.validation.ValidUUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * @author Kaminskiy Alexey
 * @since 2020.03
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeVotesDTO {
    private UUID id;
    @ValidUUID
    private UUID recipeId;
    @ValidUUID
    private UUID userId;
    @JsonProperty
    private boolean negativeVote = false;
    @JsonProperty
    private boolean positiveVote = false;
}
