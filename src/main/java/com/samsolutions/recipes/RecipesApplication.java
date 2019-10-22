package com.samsolutions.recipes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RecipesApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecipesApplication.class, args);
        TestLog4j testLog4j = new TestLog4j();
        testLog4j.doSomething();
        System.out.println("Hello World");
    }

}
