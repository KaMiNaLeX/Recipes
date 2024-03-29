package com.samsolutions.recipes.dto;

import com.samsolutions.recipes.service.validation.ValidEmail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotBlank;

/**
 * @author kaminskiy.alexey
 * @since 2019.12
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class RegistrationUserDTO {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @ValidEmail
    private String email;
    @NotBlank
    private String login;
    @NotBlank
    private String password;

}
