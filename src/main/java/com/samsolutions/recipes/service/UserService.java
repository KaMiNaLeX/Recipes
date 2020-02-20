package com.samsolutions.recipes.service;

import com.samsolutions.recipes.dto.UserDTO;
import com.samsolutions.recipes.model.UserEntity;

import java.util.List;
import java.util.UUID;

/**
 * User service.
 *
 * @author kaminskiy.alexey
 * @since 2019.10
 */
public interface UserService {
    UserDTO getById(UUID uuid);

    UserDTO getByLogin(String login);

    void removeByLogin(String login);

    void removeById(UUID uuid);

    UserDTO createUser(UserDTO userDTO);

    List<UserDTO> findAll();

    List<UserEntity> getAll(int page, int size);

    UserDTO updateUser(UUID uuid, UserDTO userDTO);

    UserDTO getByEmail(String email);

    Boolean checkPassword(UUID uuid, String pass);

    void savePassword(UUID uuid, String pass);
}
