package com.samsolutions.recipes.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

/**
 * A user is defined by uuid; it has a firstName,lastName,email,login,password.
 *
 * @author kaminskiy.alexey
 * @since 2019.10
 */
@Entity
@Data
@Table(name = "user")
public class UserEntity extends BaseEntity {
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String email;
    @Column
    private String login;
    @Column
    private String password;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<RoleEntity> roles;

    /*
    @OneToMany
    private Set<UserRoleEntity> roles;
    */

}
