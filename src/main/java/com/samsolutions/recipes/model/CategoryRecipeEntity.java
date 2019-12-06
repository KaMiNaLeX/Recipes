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
 * @since 2019.12
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "category_recipe")
public class CategoryRecipeEntity extends BaseEntity {

    @Column(name = "recipe_id")
    private UUID recipeId;

    @Column(name = "category_id")
    private UUID categoryId;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "recipe_id", referencedColumnName = "id", insertable = false, updatable = false)
    private RecipeEntity recipe;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "category_id", referencedColumnName = "id", insertable = false, updatable = false)
    private CategoryEntity category;
}
