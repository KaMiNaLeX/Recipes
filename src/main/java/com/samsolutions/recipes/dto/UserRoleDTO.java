package com.samsolutions.recipes.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * UserRole DTO.
 *
 * @author kaminskiy.alexey
 * @since 2019.10
 */
@Getter
@Setter
public class UserRoleDTO {
    private List<RoleDTO> roleId;
    private List<UserDTO> userId;
}
