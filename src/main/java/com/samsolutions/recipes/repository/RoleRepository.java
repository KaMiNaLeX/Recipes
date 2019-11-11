package com.samsolutions.recipes.repository;

import com.samsolutions.recipes.model.RoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author kaminskiy.alexey
 * @since 2019.11
 */
@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, UUID> {
    RoleEntity findByRole(String role);
}
