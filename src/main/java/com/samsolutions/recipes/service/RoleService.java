package com.samsolutions.recipes.service;

import com.samsolutions.recipes.model.RoleEntity;
import org.springframework.stereotype.Service;

/**
 * @author kaminskiy.alexey
 * @since 2019.11
 */
@Service
public interface RoleService {

    RoleEntity findByRole(String role);
}
