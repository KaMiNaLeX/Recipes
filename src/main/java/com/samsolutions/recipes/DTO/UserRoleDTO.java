package com.samsolutions.recipes.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserRoleDTO {
    private List<RoleDTO> roleId;
    private List<UserDTO> userId;
}
