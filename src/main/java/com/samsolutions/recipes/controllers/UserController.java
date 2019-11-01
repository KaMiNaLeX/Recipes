package com.samsolutions.recipes.controllers;

import com.samsolutions.recipes.DTO.UserDTO;
import com.samsolutions.recipes.models.UserEntity;
import com.samsolutions.recipes.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * @author kaminskiy.alexey
 * @since 2019.10
 */
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserServiceImpl userService;

    @GetMapping("/getAll")
    public List<UserEntity> getAll(@RequestParam("page") int page, @RequestParam("size") int size) {
        return userService.getAll(page, size);
    }

    @GetMapping("/")
    public List<UserDTO> findAll() {
        return userService.findAll();
    }

    @GetMapping("/id/ {id}")
    public UserEntity getById(@PathVariable("id") UUID uuid) {
        return userService.getById(uuid);
    }

    @GetMapping("/login/{login}")
    public UserEntity getByLogin(@PathVariable("login") String login) {
        return userService.getByLogin(login);
    }

    @DeleteMapping("/delete/{login}")
    public void removeByLogin(@PathVariable("login") String login) {
        userService.removeByLogin(login);
    }

    @PostMapping("/create")
    public UserEntity createUser(@RequestBody UserEntity userEntity) {
        return userService.createUser(userEntity);
    }
}
