package com.samsolutions.recipes.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

/**
 * @author Kaminskiy Alexey
 * @since 2020.03
 **/
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "recipe_votes")
public class RecipeVotesEntity extends BaseEntity {
    @Column(name = "recipe_id")
    private UUID recipeId;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "positive_vote")
    private boolean positiveVote = false;

    @Column(name = "negative_vote")
    private boolean negativeVote = false;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "recipe_id", referencedColumnName = "id", insertable = false, updatable = false)
    private RecipeEntity recipe;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private UserEntity user;
}
