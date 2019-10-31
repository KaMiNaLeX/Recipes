package com.samsolutions.recipes.DTO;

import lombok.Getter;
import lombok.Setter;

/**
 * @author kaminskiy.alexey
 * @since 2019.10
 */
@Getter
@Setter
public class UserDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String login;
}
