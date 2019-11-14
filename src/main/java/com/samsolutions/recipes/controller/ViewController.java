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

@Controller
@RequestMapping("/client")
public class ViewController {
    @Autowired
    UserService userService;

    @GetMapping("/profile/{login}")
    public String showProfileForm(@PathVariable("login") String login, Model model) {
        userService.showProfileForm(login, model);
        return "profile";
    }

    @GetMapping("/index")
    public String showIndexPage() {
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
    public String showRegistrationForm(UserEntity userEntity) {
        return "registration";
    }

}
