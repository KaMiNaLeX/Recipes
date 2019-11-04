package com.samsolutions.recipes.controllers;

import com.samsolutions.recipes.models.UserEntity;
import com.samsolutions.recipes.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.UUID;

/**
 * @author kaminskiy.alexey
 * @since 2019.10
 */
@Controller
@RequestMapping("/view")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/signup")
    public String showSignUpForm(UserEntity userEntity) {
        return "add-user";
    }

    @PostMapping("/adduser")
    public String addUser(@Valid UserEntity userEntity, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-user";
        }
        userService.addUser(userEntity, result, model);
        return "index";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") UUID uuid, Model model) {
        userService.showUpdateForm(uuid, model);
        return "update-user";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") UUID uuid, @Valid UserEntity userEntity, BindingResult result, Model model) {
        if (result.hasErrors()) {
            userEntity.setId(uuid);
            return "update-user";
        }
        userService.updateUser(uuid, userEntity, result, model);
        return "index";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") UUID uuid, Model model) {
        userService.deleteUser(uuid, model);
        return "index";
    }
}
