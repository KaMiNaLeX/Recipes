package com.samsolutions.recipes.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Data
@Table(name = "user_role")
public class UserRoleEntity extends BaseEntity {
    @Column(name = "user_id")
    private UUID userId;
    @Column(name = "role_id")
    private UUID roleId;

}
