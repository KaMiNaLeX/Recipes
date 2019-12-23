package com.samsolutions.recipes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * User DTO.
 *
 * @author kaminskiy.alexey
 * @since 2019.10
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String login;
    private String password;
}
