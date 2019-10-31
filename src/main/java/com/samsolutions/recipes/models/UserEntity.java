package com.samsolutions.recipes.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author kaminskiy.alexey
 * @since 2019.10
 */
@Entity
@Data
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
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

}
