package com.samsolutions.recipes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.samsolutions.recipes.model.Enum.RoleName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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

    @JsonIgnore
    public List<UserRoleEntity> getUserRoles() {
        return userRoles;
    }

    @JsonIgnore
    public void setUserRoles(List<UserRoleEntity> userRoles) {
        this.userRoles = userRoles;
    }
}
