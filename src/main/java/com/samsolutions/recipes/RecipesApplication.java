package com.samsolutions.recipes;

import com.samsolutions.recipes.DTO.UserDto;
import com.samsolutions.recipes.models.UserEntity;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author kaminskiy.alexey
 * @since 2019.10
 */
@SpringBootApplication
public class RecipesApplication {

    public static void main(String[] args) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(2);
        List userEntities = Collections.singletonList(userEntity);
        List userDtos = new ArrayList();
        userDtos.addAll(userEntities);
        SpringApplication.run(RecipesApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext applicationContext) {
        return args -> {
            System.out.println("The beans provided by Spring Boot:");
            String[] beanNames = applicationContext.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }


        };
    }
}
