package com.samsolutions.recipes.dto;

import com.samsolutions.recipes.model.Enum.CookingDifficulty;
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
    private CookingDifficulty cookingDifficulty;
    @NotNull
    private int cookingTime;
    @NotNull
    private int positiveVotes;
    @NotNull
    private int negativeVotes;
    private Date lastModified;
    private String imgSource;
    @NotNull
    private UUID authorId;

}
