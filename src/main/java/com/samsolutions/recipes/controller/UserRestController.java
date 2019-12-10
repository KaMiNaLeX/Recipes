package com.samsolutions.recipes.controller;

import com.samsolutions.recipes.dto.UserDTO;
import com.samsolutions.recipes.model.UserEntity;
import com.samsolutions.recipes.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * User RestController to manage users.
 *
 * @author kaminskiy.alexey
 * @since 2019.10
 */
@RestController
@RequestMapping("/api/user")
public class UserRestController {
    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/getAll")
    public List<UserEntity> getAll(@RequestParam("page") int page, @RequestParam("size") int size) {
        return userService.getAll(page, size);
    }

    @GetMapping("/email/{email}")
    public UserDTO getByEmail(@PathVariable("email") String email) {
        return userService.getByEmail(email);
    }

    @GetMapping("/")
    public List<UserDTO> findAll() {
        return userService.findAll();
    }

    @GetMapping("/id/{id}")
    public UserDTO getById(@PathVariable("id") UUID uuid) {
        return userService.getById(uuid);
    }

    @GetMapping("/login/{login}")
    public UserDTO getByLogin(@PathVariable("login") String login) {
        return userService.getByLogin(login);
    }

    @DeleteMapping("/delete/{login}")
    public void removeByLogin(@PathVariable("login") String login) {
        userService.removeByLogin(login);
    }

    @DeleteMapping("/delete/id/{id}")
    public void removeById(@PathVariable("id") UUID uuid) {
        userService.removeById(uuid);
    }

    @PostMapping("/create")
    public UserEntity createUser(@RequestBody UserEntity userEntity) {
        return userService.createUser(userEntity);
    }

    @PutMapping("/{id}")
    public UserEntity updateUser(@PathVariable("id") UUID uuid, @RequestBody UserEntity userEntity) {
        return userService.updateUser(uuid, userEntity);
    }

}
