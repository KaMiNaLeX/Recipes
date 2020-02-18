package com.samsolutions.recipes.controller;

import com.samsolutions.recipes.dto.UserDTO;
import com.samsolutions.recipes.exception.CustomGlobalExceptionHandler;
import com.samsolutions.recipes.model.UserEntity;
import com.samsolutions.recipes.service.impl.UserServiceImpl;
import com.samsolutions.recipes.service.validation.ValidUUID;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

/**
 * User RestController to manage users.
 *
 * @author kaminskiy.alexey
 * @since 2019.10
 */
@Log4j2
@Validated
@RestController
@RequestMapping("/api/user")
public class UserRestController extends CustomGlobalExceptionHandler {
    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/getAll")
    public List<UserEntity> getAll(@RequestParam("page") int page, @RequestParam("size") int size) {
        return userService.getAll(page, size);
    }

    @GetMapping("/email/{email}")
    public UserDTO getByEmail(@PathVariable("email") @NotBlank String email) {
        return userService.getByEmail(email);
    }

    @GetMapping("/")
    public List<UserDTO> findAll() {
        return userService.findAll();
    }

    @GetMapping("/id/{id}")
    public UserDTO getById(@PathVariable("id") @ValidUUID UUID uuid) {
        return userService.getById(uuid);
    }

    @GetMapping("/login/{login}")
    public UserDTO getByLogin(@PathVariable("login") @NotBlank String login) {
        return userService.getByLogin(login);
    }

    @DeleteMapping("/delete/{login}")
    public void removeByLogin(@PathVariable("login") @NotBlank String login) {
        userService.removeByLogin(login);
    }

    @DeleteMapping("/delete/id/{id}")
    public void removeById(@PathVariable("id") @ValidUUID UUID uuid) {
        userService.removeById(uuid);
    }

    @PostMapping("/create")
    public UserDTO createUser(@Valid @RequestBody UserDTO userDTO) {
        try {
            log.info("User " + userDTO.getLogin() + " is created");
            return userService.createUser(userDTO);
        } catch (Exception e) {
            log.error("Create user " + userDTO.getLogin() + " is failed", e.getCause());
            return null;
        }
    }

    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable("id") @ValidUUID UUID uuid,@Valid @RequestBody UserDTO userDTO) {
        try {
            log.info("User " + userDTO.getLogin() + " is updated");
            return userService.updateUser(uuid, userDTO);
        } catch (Exception e) {
            log.error("Update user " + userDTO.getLogin() + " is failed", e.getCause());
            return null;
        }
    }

    @PutMapping("/checkPass/{id}")
    public Boolean checkPass(@PathVariable("id") @ValidUUID UUID uuid, @RequestBody @NotBlank String pass) {
        return userService.checkPassword(uuid, pass);
    }

    @PutMapping("/savePass/{id}")
    public void savePass(@PathVariable("id") @ValidUUID UUID uuid, @RequestBody @NotBlank String pass) {
        log.info("Password is updated");
        userService.savePassword(uuid, pass);
    }

}
