package com.samsolutions.recipes.DTO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author kaminskiy.alexey
 * @since 2019.10
 */
@Getter
@Setter
public class UserDto implements Serializable {
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private String login;
}
