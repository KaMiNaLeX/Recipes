package com.samsolutions.recipes.model;

import com.samsolutions.recipes.model.Enum.Unit;
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
@Table(name = "recipe_ingredient")
public class RecipeIngredientEntity extends BaseEntity {

    @Column(name = "recipe_id")
    private UUID recipeId;

    @Column(name = "ingredient_id")
    private UUID ingredientId;

    @Column(name = "amount")
    private float amount;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private Unit unit;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private Unit unitRu;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "recipe_id", referencedColumnName = "id", insertable = false, updatable = false)
    private RecipeEntity recipe;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "ingredient_id", referencedColumnName = "id", insertable = false, updatable = false)
    private IngredientEntity ingredient;

}
