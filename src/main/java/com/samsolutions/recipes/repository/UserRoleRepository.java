package com.samsolutions.recipes.repository;

import com.samsolutions.recipes.model.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * UserRole Repository.
 *
 * @author kaminskiy.alexey
 * @since 2019.10
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, UUID> {

}
