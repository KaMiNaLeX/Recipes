package com.samsolutions.recipes.dto;

import com.samsolutions.recipes.model.Enum.CookingDifficulty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Map;
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
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String COOKINGDIFFICULTY = "cookingDifficulty";
    public static final String COOKINGTIME = "cookingTime";
    public static final String POSITIVEVOTES = "positiveVotes";
    public static final String NEGATIVEVOTES = "negativeVotes";
    public static final String LASTMODIFIED = "lastModified";

    private UUID id;
    private String name;
    private CookingDifficulty cookingDifficulty;
    private int cookingTime;
    private int positiveVotes;
    private int negativeVotes;
    private Date lastModified;
    private UUID authorId;

    public RecipeDTO(Map<String, Object> values) {
        this.id = values.get(ID) != null ? (UUID) values.get(ID) : null;
        this.name = values.get(NAME) != null ? (String) values.get(NAME) : null;
        this.cookingDifficulty = values.get(COOKINGDIFFICULTY) != null ? (CookingDifficulty) values.get(COOKINGDIFFICULTY) : null;
        this.cookingTime = values.get(COOKINGTIME) != null ? (int) values.get(COOKINGTIME) : null;
        this.negativeVotes = values.get(NEGATIVEVOTES) != null ? (int) values.get(NEGATIVEVOTES) : null;
        this.lastModified = values.get(LASTMODIFIED) != null ? (Date) values.get(LASTMODIFIED) : null;
        this.positiveVotes = values.get(POSITIVEVOTES) != null ? (int) values.get(POSITIVEVOTES) : null;
    }
}
