package com.samsolutions.recipes.repository;

import com.samsolutions.recipes.model.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, UUID>, BaseRepository<UserRoleEntity> {
    UserRoleEntity findByUserIdAndRoleId(UUID userId, UUID roleId);
}
