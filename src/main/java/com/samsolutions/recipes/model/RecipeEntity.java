package com.samsolutions.recipes.model;

import com.samsolutions.recipes.model.Enum.CookingDifficulty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author kaminskiy.alexey
 * @since 2019.12
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "recipe")
public class RecipeEntity extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "name_ru")
    private String nameRu;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private CookingDifficulty cookingDifficulty;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private CookingDifficulty cookingDifficultyRu;

    @Column(name = "cooking_time")
    private int cookingTime;

    @Column(name = "positive_votes")
    private int positiveVotes;

    @Column(name = "negative_votes")
    private int negativeVotes;

    @Column(name = "last_modified")
    private Date lastModified = new Date();

    @Column(name = "img_source")
    private String imgSource;

    @Column(name = "author_id")
    private UUID authorId;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "author_id", referencedColumnName = "id", insertable = false, updatable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "recipe",
            cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE},
            orphanRemoval = true
    )
    private List<CookingStepsEntity> cookingStepsEntityList;

    @OneToMany(mappedBy = "recipe",
            cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE},
            orphanRemoval = true
    )
    private List<FavoriteEntity> favoriteEntityList;

    @OneToMany(mappedBy = "recipe",
            cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE},
            orphanRemoval = true
    )
    private List<RecipeIngredientEntity> recipeIngredientEntityList;

    @OneToMany(mappedBy = "recipe",
            cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE},
            orphanRemoval = true
    )
    private List<CategoryRecipeEntity> categoryRecipeEntities;

    @OneToMany(mappedBy = "recipe",
            cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE},
            orphanRemoval = true
    )
    private List<CommentsEntity> commentsEntityList;

    @OneToMany(mappedBy = "recipe",
            cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE},
            orphanRemoval = true
    )
    private List<RecipeVotesEntity> votesEntityList;

}
