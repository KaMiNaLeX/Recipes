package com.samsolutions.recipes;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import java.util.Arrays;


/**
 * @author kaminskiy.alexey
 * @since 2019.10
 */
@SpringBootApplication
public class RecipesApplication {

    public static void main(String[] args) {

        Faker faker = new Faker();

        String streetName = faker.address().streetName();
        String number = faker.address().buildingNumber();
        String city = faker.address().city();
        String country = faker.address().country();
        String a = faker.funnyName().name();


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
