package com.samsolutions.recipes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

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

    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
}
