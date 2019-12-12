package com.samsolutions.recipes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

/**
 * UserRole entity.
 *
 * @author kaminskiy.alexey
 * @since 2019.10
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_role")
public class UserRoleEntity {

    @Id
    @Column(name = "created_on")
    private Date createdOn = new Date();

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "role_id")
    private UUID roleId;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private UserEntity user;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "role_id", referencedColumnName = "id", insertable = false, updatable = false)
    private RoleEntity role;

    @JsonIgnore
    public UserEntity getUser() {
        return user;
    }

    @JsonIgnore
    public void setUser(UserEntity user) {
        this.user = user;
    }

    @JsonIgnore
    public RoleEntity getRole() {
        return role;
    }

    @JsonIgnore
    public void setRole(RoleEntity role) {
        this.role = role;
    }
}
