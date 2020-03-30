package com.samsolutions.recipes.dto;

import com.samsolutions.recipes.model.Enum.CookingDifficulty;
import com.samsolutions.recipes.service.validation.ValidUUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

/**
 * @author kaminskiy.alexey
 * @since 2019.12
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDTO {
    private UUID id;
    @NotBlank
    private String name;
    @NotBlank
    private String nameRu;
    private CookingDifficulty cookingDifficulty;
    private CookingDifficulty cookingDifficultyRu;
    @NotNull
    private int cookingTime;
    @NotNull
    private int positiveVotes;
    @NotNull
    private int negativeVotes;
    private Date lastModified;
    private String imgSource;
    @ValidUUID
    private UUID authorId;
}
