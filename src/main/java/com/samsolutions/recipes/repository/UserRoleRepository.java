package com.samsolutions.recipes.repository;

import com.samsolutions.recipes.model.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, UUID> {
    UserRoleEntity findByUserIdAndRoleId(UUID userId, UUID roleId);

    void deleteByUserId(UUID userId);

    List<UserRoleEntity> getByUserId(UUID userId);

}
