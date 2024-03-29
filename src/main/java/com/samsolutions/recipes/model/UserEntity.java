package com.samsolutions.recipes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * A user is defined by uuid; it has a firstName,lastName,email,login,password.
 *
 * @author kaminskiy.alexey
 * @since 2019.10
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToMany(mappedBy = "user",
            cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE},
            orphanRemoval = true
    )
    private List<UserRoleEntity> userRoles;

    @OneToMany(mappedBy = "user",
            cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE},
            orphanRemoval = true
    )
    private List<FavoriteEntity> favoriteEntityList;

    @OneToMany(mappedBy = "user",
            cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE},
            orphanRemoval = true
    )
    private List<RecipeEntity> recipeEntityList;

    @OneToMany(mappedBy = "user",
            cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE},
            orphanRemoval = true
    )
    private List<CommentsEntity> commentsEntityList;

    @OneToMany(mappedBy = "user",
            cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE},
            orphanRemoval = true
    )
    private List<RecipeVotesEntity> votesEntityList;

    @JsonIgnore
    public List<UserRoleEntity> getUserRoles() {
        return userRoles;
    }

}
