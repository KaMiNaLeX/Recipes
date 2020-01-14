package com.samsolutions.recipes.integration.repository;

import com.samsolutions.recipes.BaseTest;
import com.samsolutions.recipes.model.UserEntity;
import com.samsolutions.recipes.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryTestIT extends BaseTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TestEntityManager entityManager;

    @Before
    public void setUp() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("test");
        userEntity.setEmail("test");
        userEntity.setPassword("test");
        userEntity.setFirstName("test");
        userEntity.setLastName("test");

        Mockito.when(userRepository.getByLogin(userEntity.getLogin()))
                .thenReturn(userEntity);
    }

    @Test
    public void shouldReturnOneUser() {
        //given
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("test");
        userEntity.setEmail("test");
        userEntity.setPassword("test");
        userEntity.setFirstName("test");
        userEntity.setLastName("test");

        entityManager.persistAndFlush(userEntity);
        //when
        UserEntity found = userRepository.getByLogin(userEntity.getLogin());

        //then
        assertThat(found.getId()).isEqualTo(userEntity.getId());
    }
}
