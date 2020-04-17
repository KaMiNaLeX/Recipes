package com.samsolutions.recipes.dto;

import com.samsolutions.recipes.service.validation.ValidUUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.UUID;

/**
 * @author Kaminskiy Alexey
 * @since 2020.03
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentsDTO {
    private UUID id;
    @NotBlank
    private String text;
    private Date creationDate;
    private Date updateDate;
    @ValidUUID
    private UUID recipeId;
    @ValidUUID
    private UUID creatorId;
    private String creatorName;
}
