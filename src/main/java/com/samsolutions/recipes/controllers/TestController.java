package com.samsolutions.recipes.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class TestController {

    @RequestMapping("/")
    public String hello() {
        return "Hello world Hibernate2";
    }

    @RequestMapping("/bye")
    public String bye() {
        return "Bye!";
    }
}
