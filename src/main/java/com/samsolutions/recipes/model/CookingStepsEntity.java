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
 * @author kaminskiy.alexey
 * @since 2019.11
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cooking_steps")
public class CookingStepsEntity extends BaseEntity {

    @Column(name = "description")
    private String description;

    @Column(name = "description_ru")
    private String descriptionRu;

    @Column(name = "img_source")
    private String imgSource;

    @Column(name = "active")
    private boolean active;

    @Column(name = "recipe_id")
    private UUID recipeId;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "recipe_id", referencedColumnName = "id", insertable = false, updatable = false)
    private RecipeEntity recipe;
}
