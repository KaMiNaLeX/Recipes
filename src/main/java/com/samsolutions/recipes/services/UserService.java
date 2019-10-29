package com.samsolutions.recipes.services;

import com.samsolutions.recipes.models.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author kaminskiy.alexey
 * @since 2019.10
 */
@Service
public interface UserService {
    List<UserEntity> findAllById(int Id);
    List<UserEntity> findAll();
}
