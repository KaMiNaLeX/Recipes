package com.samsolutions.recipes.services.impl;

import com.samsolutions.recipes.models.UserEntity;
import com.samsolutions.recipes.repositories.UserRepository;
import com.samsolutions.recipes.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kaminskiy.alexey
 * @since 2019.10
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List findAllById(int id) {
        List<UserEntity> userEntities = new ArrayList<>();
        userEntities = userRepository.findAllById(id);
        return userEntities;
    }

    @Override
    public List findAll() {
        List<UserEntity> userEntities = new ArrayList<>();
        userEntities = userRepository.findAll();
        return userEntities;
    }
}
