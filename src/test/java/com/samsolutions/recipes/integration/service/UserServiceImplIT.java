package com.samsolutions.recipes.integration.service;

import com.samsolutions.recipes.BaseTest;
import com.samsolutions.recipes.model.UserEntity;
import com.samsolutions.recipes.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class UserServiceImplIT extends BaseTest {
    @MockBean
    private UserRepository userRepository;

    @Before
    public void setUp() {
        UserEntity userEntity = createUser();
        Mockito.when(userRepository.getByLogin(userEntity.getLogin()))
                .thenReturn(userEntity);
    }

    @Test
    public void shouldReturnOneUserByLogin() {
        String name = "test";
        UserEntity found = userRepository.getByLogin(name);
        assertThat(found.getLogin())
                .isEqualTo(name);
    }

    @Test
    public void shouldAddOneIUser() {
        UserEntity userEntity = createUser();
        userRepository.save(userEntity);
        when(userRepository.getByLogin(userEntity.getLogin())).thenReturn(userEntity);
        UserEntity found = userRepository.getByLogin(userEntity.getLogin());

        assertThat(found)
                .isEqualTo(userEntity);
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
