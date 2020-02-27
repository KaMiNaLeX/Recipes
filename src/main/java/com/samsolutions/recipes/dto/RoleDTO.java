package com.samsolutions.recipes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

/**
 * Role DTO.
 *
 * @author kaminskiy.alexey
 * @since 2019.10
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    private UUID id;
    @NotBlank
    private String name;
    @NotBlank
    private String nameRu;
    private String description;
    private String descriptionRu;

}
