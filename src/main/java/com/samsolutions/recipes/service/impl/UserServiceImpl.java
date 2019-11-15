package com.samsolutions.recipes.service.impl;

import com.samsolutions.recipes.DTO.UserDTO;
import com.samsolutions.recipes.exeption.UserNotFoundException;
import com.samsolutions.recipes.model.RoleEntity;
import com.samsolutions.recipes.model.UserEntity;
import com.samsolutions.recipes.model.UserRoleEntity;
import com.samsolutions.recipes.repository.RoleRepository;
import com.samsolutions.recipes.repository.UserRepository;
import com.samsolutions.recipes.repository.UserRoleRepository;
import com.samsolutions.recipes.service.ModelMapperService;
import com.samsolutions.recipes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.*;


/**
 * The service has been annotated with @Service so that Spring Boot will detect it and
 * create an instance of it.
 *
 * @author kaminskiy.alexey
 * @since 2019.10
 */
@Service
public class UserServiceImpl implements UserService, ModelMapperService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserEntity getById(UUID uuid) {
        UserEntity userEntity = userRepository.getById(uuid);
        if (userEntity == null) {
            throw new UserNotFoundException(String.format("User with id %s not found", uuid));
        }
        return userEntity;
    }

    @Override
    public UserEntity getByLogin(String login) {
        UserEntity userEntity = userRepository.getByLogin(login);
        if (userEntity == null) {
            throw new UserNotFoundException(String.format("User with id %s not found", login));
        }
        return userEntity;
    }


    @Override
    public void removeByLogin(String login) {
        UserEntity userEntity = userRepository.getByLogin(login);
        if (userEntity == null) {
            throw new UserNotFoundException(String.format("User with id %s not found", login));
        }
        userRepository.delete(userEntity);
    }

    @Override
    public void removeById(UUID uuid) {
        UserEntity userEntity = userRepository.getById(uuid);
        if (userEntity == null) {
            throw new UserNotFoundException(String.format("User with id %s not found", uuid));
        }
        userRepository.delete(userEntity);
    }

    @Override
    public UserEntity createUser(UserEntity userEntity) {
        UserEntity newUserEntity = new UserEntity();
        newUserEntity.setFirstName(userEntity.getFirstName());
        newUserEntity.setLastName(userEntity.getLastName());
        newUserEntity.setEmail(userEntity.getEmail());
        newUserEntity.setLogin(userEntity.getLogin());
        newUserEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        RoleEntity userRole = roleRepository.findByName("VIEWER");
        newUserEntity.setRoles(new HashSet<RoleEntity>(Arrays.asList(userRole)));
        userRepository.save(newUserEntity);
        return newUserEntity;
    }

    @Override
    public List<UserDTO> findAll() {
        List<UserDTO> userDTOList = new ArrayList<>();
        UserDTO userDTO = new UserDTO();
        userDTOList.add(userDTO);
        map(userRepository.findAll(), userDTOList);
        return userDTOList;
    }

    @Override
    public List<UserEntity> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("login"));
        Page<UserEntity> pageEntity = userRepository.findAll(pageable);
        List<UserEntity> list = pageEntity.getContent();
        return list;
    }

    @Override
    public UserEntity updateUser(UUID uuid, UserEntity userEntity) {
        UserEntity user = userRepository.getById(uuid);
        if (userEntity == null) {
            throw new UserNotFoundException(String.format("User with id %s not found", uuid));
        }
        user.setLogin(userEntity.getLogin());
        user.setEmail(userEntity.getEmail());
        user.setFirstName(userEntity.getFirstName());
        user.setLastName(userEntity.getLastName());
        user.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        RoleEntity userRole = roleRepository.findByName("VIEWER");
        user.setRoles(new HashSet<RoleEntity>(Arrays.asList(userRole)));
        userRepository.save(user);
        return user;
    }

    @Override
    public void addUser(@Valid UserEntity userEntity, BindingResult result, Model model) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        RoleEntity userRole = roleRepository.findByName("VIEWER");
        userEntity.setRoles(new HashSet<RoleEntity>(Arrays.asList(userRole)));
        userRepository.save(userEntity);
        model.addAttribute("users", userRepository.findAll());
    }

    @Override
    public void showUpdateForm(UUID uuid, Model model) {
        UserEntity userEntity = userRepository.getById(uuid);
        if (userEntity == null) {
            throw new UserNotFoundException(String.format("User with id %s not found", uuid));
        }
        model.addAttribute("userEntity", userEntity);
    }

    @Override
    public void showProfileForm(String login, Model model) {
        UserEntity userEntity = userRepository.getByLogin(login);
        model.addAttribute("userEntity", userEntity);
    }

    @Override
    public void showEditRoleForm(String login, Model model) {
        UserEntity userEntity = userRepository.getByLogin(login);
        if (userEntity == null) {
            throw new UserNotFoundException(String.format("User with id %s not found", login));
        }
        model.addAttribute("userEntity", userEntity);
    }

    @Override
    public void updateUser(UUID uuid, UserEntity userEntity, BindingResult result, Model model) {
        userRepository.save(userEntity);
        model.addAttribute("users", userRepository.findAll());
    }

    //todo : need to fix
    @Override
    public void deleteUser(UUID uuid, Model model) {
        UserEntity userEntity = userRepository.getById(uuid);
        if (userEntity == null) {
            throw new UserNotFoundException(String.format("User with id %s not found", uuid));
        }
        userRepository.delete(userEntity);
        model.addAttribute("users", userRepository.findAll());
    }

    @Override
    public void all(Model model) {
        model.addAttribute("users", userRepository.findAll());
    }

    @Override
    @Transactional
    public void addRole(String login, String role, Model model) {
        UserEntity userEntity = userRepository.getByLogin(login);
        RoleEntity roleEntity = roleRepository.findByName(role);
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setUserId(userEntity.getId());
        userRoleEntity.setRoleId(roleEntity.getId());
        try {
            userRoleRepository.save(userRoleEntity);
        } catch (Exception e) {
            e.getMessage();
        }
        model.addAttribute("userEntity", userEntity);
    }

    @Override
    public void deleteRole(String login, String role, Model model) {
        UserEntity userEntity = userRepository.getByLogin(login);
        if (userEntity == null) {
            throw new UserNotFoundException(String.format("User with id %s not found", login));
        }
        userRepository.deleteRole(login, role);
        model.addAttribute("userEntity", userEntity);
    }
}
