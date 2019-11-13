package com.samsolutions.recipes.model;

import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author kaminskiy.alexey
 * @since 2019.11
 */
@Entity
@Data
@Table(name = "role")
public class RoleEntity extends BaseEntity {

    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;

}
