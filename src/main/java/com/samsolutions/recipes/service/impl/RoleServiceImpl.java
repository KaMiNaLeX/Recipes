package com.samsolutions.recipes.service.impl;

import com.samsolutions.recipes.model.RoleEntity;
import com.samsolutions.recipes.repository.RoleRepository;
import com.samsolutions.recipes.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author kaminskiy.alexey
 * @since 2019.11
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepository roleRepository;

    @Override
    public RoleEntity findByRole(String role) {
        RoleEntity roleEntity = roleRepository.findByRole(role);
        return roleEntity;
    }
}
