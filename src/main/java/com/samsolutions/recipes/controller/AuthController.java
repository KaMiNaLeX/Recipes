package com.samsolutions.recipes.controller;

import com.samsolutions.recipes.config.JwtTokenProvider;
import com.samsolutions.recipes.dto.AuthBodyDTO;
import com.samsolutions.recipes.dto.RegistrationUserDTO;
import com.samsolutions.recipes.exception.NotFoundException;
import com.samsolutions.recipes.model.Enum.RoleName;
import com.samsolutions.recipes.model.UserEntity;
import com.samsolutions.recipes.model.UserRoleEntity;
import com.samsolutions.recipes.repository.UserRepository;
import com.samsolutions.recipes.service.impl.CustomUserDetailsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

/**
 * Auth controller to login users.
 *
 * @author kaminskiy.alexey
 * @since 2019.12
 */
@Log4j2
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository users;

    @Autowired
    private CustomUserDetailsService userService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthBodyDTO data) {
        try {
            String username = data.getEmail();
            UserEntity user = userService.findUserByEmail(username);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
            String token = jwtTokenProvider.createToken(username, this.users.findByEmail(username).getUserRoles());
            Map<Object, Object> model = new HashMap<>();
            model.put("id", user.getId());
            model.put("username", username);
            model.put("token", token);
            return ok(model);
        } catch (AuthenticationException e) {
            log.error(new BadCredentialsException("Invalid email/password supplied"));
            return notFound().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegistrationUserDTO user) {
        UserEntity userExists = userService.findUserByEmail(user.getEmail());
        UserEntity userExists2 = userService.getByLogin(user.getLogin());
        if (userExists != null) {
            log.error(new IllegalArgumentException("User with email: " + user.getEmail() + " already exists"));
        }
        if (userExists2 != null) {
            log.error(new IllegalArgumentException("User with login: " + user.getLogin() + " already exists"));
        }
        userService.saveUser(user);
        Map<Object, Object> model = new HashMap<>();
        model.put("message", "User registered successfully");
        log.info("User registered successfully");
        return ok(model);
    }

    @GetMapping("/user")
    public ResponseEntity user(Principal user) {
        if (user == null) {
            log.error(new BadCredentialsException("User does not login"));
            return null;
        } else {
            Map<Object, Object> model = new HashMap<>();
            model.put("principal", "true");
            return ok(model);
        }
    }

    @GetMapping("/role")
    public ResponseEntity userRole(Principal user) {
        try {
            if (user == null) {
                log.error(new BadCredentialsException("User does not login"));
            }
            String username = user.getName();
            UserEntity userEntity = userService.findUserByEmail(username);
            List<UserRoleEntity> userRoleList = userEntity.getUserRoles();
            List<RoleName> roleNameList = new ArrayList<>();
            for (UserRoleEntity userRoleEntity : userRoleList) {
                RoleName roleName = userRoleEntity.getRole().getName();
                roleNameList.add(roleName);
            }
            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("roles", roleNameList);
            return ok(model);
        } catch (NotFoundException ex) {
            log.error(new NotFoundException("NOT_FOUND!"));
            return null;
        }
    }
}
