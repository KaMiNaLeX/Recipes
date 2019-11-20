package com.samsolutions.recipes.repository;

import com.samsolutions.recipes.model.RoleEntity;
import com.samsolutions.recipes.model.RoleName;
import org.springframework.stereotype.Repository;

/**
 * @author kaminskiy.alexey
 * @since 2019.11
 */
@Repository
public interface RoleRepository extends BaseRepository<RoleEntity> {

    RoleEntity findByName(RoleName name);
}
