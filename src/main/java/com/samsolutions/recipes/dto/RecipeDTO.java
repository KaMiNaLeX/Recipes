package com.samsolutions.recipes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Map;

/**
 * @author kaminskiy.alexey
 * @since 2019.12
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDTO {
    public static final String NAME = "name";
    public static final String COOKINGDIFFICULTY = "cookingDifficulty";
    public static final String COOKINGTIME = "cookingTime";
    public static final String POSITIVEVOTES = "positiveVotes";
    public static final String NEGATIVEVOTES = "negativeVotes";
    public static final String LASTMODIFIED = "lastModified";
    public static final String AUTHOR = "author";

    private String name;
    private String cookingDifficulty;
    private int cookingTime;
    private int positiveVotes;
    private int negativeVotes;
    private Date lastModified;
    private String author;

    public RecipeDTO(Map<String, Object> values) {
        this.name = values.get(NAME) != null ? (String) values.get(NAME) : null;
        this.cookingDifficulty = values.get(COOKINGDIFFICULTY) != null ? (String) values.get(COOKINGDIFFICULTY) : null;
        this.cookingTime = values.get(COOKINGTIME) != null ? (int) values.get(COOKINGTIME) : null;
        this.negativeVotes = values.get(NEGATIVEVOTES) != null ? (int) values.get(NEGATIVEVOTES) : null;
        this.lastModified = values.get(LASTMODIFIED) != null ? (Date) values.get(LASTMODIFIED) : null;
        this.author = values.get(AUTHOR) != null ? (String) values.get(AUTHOR) : null;
        this.positiveVotes = values.get(POSITIVEVOTES) != null ? (int) values.get(POSITIVEVOTES) : null;
    }
}
