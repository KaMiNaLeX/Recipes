package com.samsolutions.recipes.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * A category is defined by uuid; it has a name.
 *
 * @author kaminskiy.alexey
 * @since 2019.11
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "category")
public class CategoryEntity extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "tag")
    private String tag;

    @Column(name = "img_source")
    private String imgSource;

    @OneToMany(mappedBy = "category",
            cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE},
            orphanRemoval = true
    )
    private List<CategoryRecipeEntity> categoryRecipeEntities;

}
