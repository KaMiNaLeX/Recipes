package com.samsolutions.recipes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

/**
 * @author kaminskiy.alexey
 * @since 2019.12
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private UUID id;
    @NotBlank
    private String name;
    @NotBlank
    private String nameRu;
    @NotBlank
    private String description;
    @NotBlank
    private String descriptionRu;
    @NotBlank
    private String tag;
    @NotBlank
    private String tagRu;
    private String imgSource;
}
