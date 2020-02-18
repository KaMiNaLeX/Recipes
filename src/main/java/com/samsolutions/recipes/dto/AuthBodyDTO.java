package com.samsolutions.recipes.dto;

import com.samsolutions.recipes.service.validation.ValidEmail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

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
    @NotBlank
    @ValidEmail
    private String email;
    @NotBlank
    private String password;
}
