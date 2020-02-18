package com.samsolutions.recipes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
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
    private String email;
    @NotBlank
    @Min(5)
    private String password;
}
