package com.samsolutions.recipes.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;
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
@Table(name = "comments")
public class CommentsEntity extends BaseEntity {
    @Column(name = "text")
    private String text;

    @Column(name = "creation_date")
    private Date creationDate = new Date();

    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "recipe_id")
    private UUID recipeId;

    @Column(name = "creator_id")
    private UUID creatorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", referencedColumnName = "id", insertable = false, updatable = false)
    private RecipeEntity recipe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", referencedColumnName = "id", insertable = false, updatable = false)
    private UserEntity user;
}
