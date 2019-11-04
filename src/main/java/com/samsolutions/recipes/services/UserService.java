package com.samsolutions.recipes.services;

import com.samsolutions.recipes.DTO.UserDTO;
import com.samsolutions.recipes.models.UserEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

/**
 * @author kaminskiy.alexey
 * @since 2019.10
 */
@Service
public interface UserService {
    UserEntity getById(UUID uuid);

    UserEntity getByLogin(String login);

    void removeByLogin(String login);

    void removeById(UUID uuid);

    void addUser(@Valid UserEntity userEntity, BindingResult result, Model model);

    void showUpdateForm(UUID uuid, Model model);

    void updateUser(UUID uuid, UserEntity userEntity, BindingResult result, Model model);

    void deleteUser(UUID uuid, Model model);

    UserEntity createUser(UserEntity userEntity);

    List<UserDTO> findAll();

    List<UserEntity> getAll(int page, int size);

    UserEntity updateUser(UUID uuid, UserEntity userEntity);
}
