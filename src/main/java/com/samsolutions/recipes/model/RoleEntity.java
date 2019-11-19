package com.samsolutions.recipes.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * @author kaminskiy.alexey
 * @since 2019.11
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role")
public class RoleEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(length = 6)
    private RoleName name;
    @Column(name = "description")
    private String description;
    @OneToMany(
            mappedBy = "role",
            cascade = {CascadeType.REMOVE, CascadeType.REFRESH},
            orphanRemoval = true
    )
    private List<UserRoleEntity> userRoles;
}
