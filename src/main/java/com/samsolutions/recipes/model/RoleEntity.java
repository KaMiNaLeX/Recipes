package com.samsolutions.recipes.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

/**
 * @author kaminskiy.alexey
 * @since 2019.11
 */
@Entity
@Data
@Table(name = "role")
public class RoleEntity extends BaseEntity {

    @Column(name = "role")
    private String role;
    @Column(name = "description")
    private String description;

}
