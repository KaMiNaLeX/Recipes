package com.samsolutions.recipes.controllers;

import com.samsolutions.recipes.DTO.UserDto;
import com.samsolutions.recipes.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author kaminskiy.alexey
 * @since 2019.10
 */
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserServiceImpl userService;

    @GetMapping("/")
    public List<UserDto> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public List findAllById(@PathVariable("id") int id) {
        return userService.findAllById(id);
    }
}
