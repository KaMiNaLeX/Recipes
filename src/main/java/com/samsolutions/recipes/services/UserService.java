package com.samsolutions.recipes.services;

import com.samsolutions.recipes.DTO.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author kaminskiy.alexey
 * @since 2019.10
 */
@Service
public interface UserService {
    List findAllById(int id);

    List<UserDto> findAll();
}
