package com.samsolutions.recipes.service;

import com.samsolutions.recipes.DTO.UserDTO;
import com.samsolutions.recipes.model.UserEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

/**
 * User service.
 *
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

    void showUpdateForm(String login, Model model);

    void showProfileForm(UUID uuid, Model model);

    void prepareModelForEditRoleForm(String login, Model model);

    void updateUser(UserEntity userEntity, BindingResult result, Model model);

    void saveChanges(UserEntity userEntity, BindingResult result, Model model);

    void deleteUser(String login, Model model);

    void all(Model model);

    void addRole(String login, String role, Model model);

    void deleteRole(String login, String role, Model model);

    UserEntity createUser(UserEntity userEntity);

    List<UserDTO> findAll();

    List<UserEntity> getAll(int page, int size);

    UserEntity updateUser(UUID uuid, UserEntity userEntity);

    UserEntity getByEmail(String email);
}
