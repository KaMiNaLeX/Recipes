package com.samsolutions.recipes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Auth DTO.
 *
 * @author kaminskiy.alexey
 * @since 2019.12
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthBodyDTO {

    private String email;
    private String password;
}
