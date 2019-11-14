package com.samsolutions.recipes.controller;

import com.samsolutions.recipes.model.UserEntity;
import com.samsolutions.recipes.service.UserService;
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
 * User controller to manage users.
 *
 * @author kaminskiy.alexey
 * @since 2019.10
 */
@Controller
@RequestMapping("/admin")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/users")
    public String all(Model model) {
        userService.all(model);
        return "user/list";
    }

    @GetMapping("/signUp")
    public String showSignUpForm(UserEntity userEntity) {
        return "user/add";
    }

    @PostMapping("/addUser")
    public String addUser(@Valid UserEntity userEntity, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/add";
        }
        userService.addUser(userEntity, result, model);
        return "user/list";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") UUID uuid, Model model) {
        userService.showUpdateForm(uuid, model);
        return "user/edit";
    }

    @PostMapping("/update/{id}")
    public String updateUser(
            @PathVariable("id") UUID uuid, @Valid UserEntity userEntity, BindingResult result, Model model) {
        if (result.hasErrors()) {
            userEntity.setId(uuid);
            return "user/edit";
        }
        userService.updateUser(uuid, userEntity, result, model);
        return "user/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") UUID uuid, Model model) {
        userService.deleteUser(uuid, model);
        return "user/list";
    }

    @GetMapping("/editRole/{login}")
    public String showEditRoleForm(@PathVariable("login") String login, Model model) {
        userService.showEditRoleForm(login, model);
        return "user/role";
    }

    @GetMapping("/addRole/{login}/{role}")
    public String addRole(@PathVariable("login") String login, @PathVariable("role") String role, Model model) {
        userService.addRole(login, role, model);
        return "user/role";
    }

    @GetMapping("deleteRole/{login}/{role}")
    public String deleteRole(@PathVariable("login") String login, @PathVariable("role") String role, Model model) {
        userService.deleteRole(login, role, model);
        return "user/role";
    }
}
