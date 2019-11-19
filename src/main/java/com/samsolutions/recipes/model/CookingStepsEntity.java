package com.samsolutions.recipes.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

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
    @Column
    private int number;
    @Column
    private String name;
    @Column
    private String description;
    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] content;
}
