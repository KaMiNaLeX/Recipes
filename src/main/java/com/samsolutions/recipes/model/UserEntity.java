package com.samsolutions.recipes.model;

import com.samsolutions.recipes.service.security.UserRole;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

/**
 * A user is defined by uuid; it has a firstName,lastName,email,login,password.
 *
 * @author kaminskiy.alexey
 * @since 2019.10
 */
@Entity
@Data
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
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
    @Column
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

}
