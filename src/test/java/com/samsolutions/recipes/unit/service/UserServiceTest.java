package com.samsolutions.recipes.unit.service;

import com.samsolutions.recipes.BaseTest;
import com.samsolutions.recipes.dto.UserDTO;
import com.samsolutions.recipes.model.UserEntity;
import com.samsolutions.recipes.repository.UserRepository;
import com.samsolutions.recipes.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceTest extends BaseTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void getByUserId() {
        UserEntity userEntity = createUser();
        userRepository.save(userEntity);
        UserEntity found = userRepository.getById(userEntity.getId());
        UserDTO userDTO1 = userService.getById(userEntity.getId());
        assertThat(userDTO1.getId()).isEqualTo(found.getId());
    }

    @Test
    public void addUser() {
        UserEntity userEntity = createUser();
        userRepository.save(userEntity);
        UserDTO found = userService.getByLogin(userEntity.getLogin());

        assertThat(found.getLogin())
                .isEqualTo(userEntity.getLogin());
    }

    @Test
    public void shouldDeleteUser() {
        UserEntity userEntity = createUser();
        userRepository.save(userEntity);
        assertThat(userService.getById(userEntity.getId()).getLogin()).isEqualTo("test");
        userRepository.delete(userEntity);
        assertThat(userService.findAll().isEmpty());
    }

    public UserEntity createUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("test");
        userEntity.setEmail("test");
        userEntity.setPassword("test");
        userEntity.setFirstName("test");
        userEntity.setLastName("test");
        return userEntity;
    }
}
