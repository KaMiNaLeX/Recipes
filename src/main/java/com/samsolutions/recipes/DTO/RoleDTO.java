package com.samsolutions.recipes.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";

    private String name;
    private String description;

    public RoleDTO(Map<String, Object> values) {
        this.name = values.get(NAME) != null ? (String) values.get(NAME) : null;
        this.description = values.get(DESCRIPTION) != null ? (String) values.get(DESCRIPTION) : null;
    }

}
