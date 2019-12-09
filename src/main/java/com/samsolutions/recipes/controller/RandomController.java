package com.samsolutions.recipes.controller;

import com.github.javafaker.Faker;
import com.samsolutions.recipes.model.UserEntity;
import com.samsolutions.recipes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * This controller is used to generate random fake data. To the programmer not to invent values, we use a faker.
 * This controller is not used in production.
 *
 * @author kaminskiy.alexey
 * @since 2019.10
 */
@RestController
@RequestMapping("/random")
public class RandomController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/users")
    public List<UserEntity> getRandomUser() {
        Faker faker = new Faker();
        List<UserEntity> userEntityList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            UserEntity userEntity = new UserEntity();
            userEntity.setFirstName(faker.name().firstName());
            userEntity.setLastName(faker.name().lastName());
            userEntity.setEmail(faker.bothify("????##@gmail.com"));
            userEntity.setLogin(faker.witcher().monster());
            userEntity.setPassword(passwordEncoder.encode(faker.bothify("??#???#??")));
            userEntityList.add(userEntity);
            userRepository.save(userEntity);
        }
        return userEntityList;
    }
}
