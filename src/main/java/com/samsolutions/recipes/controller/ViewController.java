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
import java.security.Principal;
import java.util.UUID;

/**
 * User controller to manage users.
 *
 * @author kaminskiy.alexey
 * @since 2019.11
 */

@Controller
@RequestMapping("/client")
public class ViewController {
    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public String showProfileForm(Principal principal, Model model) {
        UUID uuid = userService.getByLogin(principal.getName()).getId();
        userService.showProfileForm(uuid, model);
        return "profile";
    }

    @PostMapping("/save/{id}")
    public String saveProfile(@PathVariable("id") UUID uuid, @Valid UserEntity userEntity,
                              BindingResult result, Model model) {
        if (result.hasErrors()) {
            userEntity.setId(uuid);
            return "profile";
        }
        userService.saveChanges(userEntity, result, model);
        return "profile";
    }

    @GetMapping("/index")
    public String showIndexPage() {
        return "index";
    }

    @GetMapping("/")
    public String showIndex() {
        return "index";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/registration")
    public String registration(@Valid UserEntity userEntity, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/add";
        }
        userService.addUser(userEntity, result, model);
        return "index";
    }

    @GetMapping("/registration")
    public String showRegistrationForm() {
        return "registration";
    }

}
