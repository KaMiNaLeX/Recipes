package com.samsolutions.recipes.repository;

import com.samsolutions.recipes.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * @author kaminskiy.alexey
 * @since 2019.11
 */
@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    RoleEntity findByRole(String role);
}
