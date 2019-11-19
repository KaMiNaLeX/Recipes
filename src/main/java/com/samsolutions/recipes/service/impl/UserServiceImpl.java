package com.samsolutions.recipes.service.impl;

import com.samsolutions.recipes.DTO.RoleDTO;
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
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


/**
 * The service has been annotated with @Service so that Spring Boot will detect it and
 * create an instance of it.
 *
 * @author kaminskiy.alexey
 * @since 2019.10
 */
@Log4j2
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
        try {
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            userRepository.save(userEntity);

            RoleEntity role = roleRepository.findByName("VIEWER");
            UserRoleEntity userRoleEntity = new UserRoleEntity();
            userRoleEntity.setUserId(userEntity.getId());
            userRoleEntity.setRoleId(role.getId());
            userRoleRepository.save(userRoleEntity);
            return userEntity;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
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
        return pageEntity.getContent();
    }

    @Override
    public UserEntity updateUser(UUID uuid, UserEntity userEntity) {
        try {
            UserEntity newUserEntity = userRepository.getById(uuid);
            newUserEntity.setFirstName(userEntity.getFirstName());
            newUserEntity.setLastName(userEntity.getLastName());
            newUserEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            newUserEntity.setLogin(userEntity.getLogin());
            newUserEntity.setEmail(userEntity.getEmail());
            userRepository.save(newUserEntity);
            return newUserEntity;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;

    }

    @Override
    public void addUser(@Valid UserEntity userEntity, BindingResult result, Model model) {
        try {
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            userRepository.save(userEntity);

            RoleEntity role = roleRepository.findByName("VIEWER");
            UserRoleEntity userRoleEntity = new UserRoleEntity();
            userRoleEntity.setUserId(userEntity.getId());
            userRoleEntity.setRoleId(role.getId());
            userRoleRepository.save(userRoleEntity);
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            model.addAttribute("users", userRepository.findAll());
        }
    }

    @Override
    public void showUpdateForm(String login, Model model) {
        UserEntity userEntity = userRepository.getByLogin(login);
        model.addAttribute("userEntity", userEntity);
    }

    @Override
    public void showProfileForm(UUID uuid, Model model) {
        UserEntity userEntity = userRepository.getById(uuid);
        model.addAttribute("userEntity", userEntity);
    }

    @Override
    public void prepareModelForEditRoleForm(String login, Model model) {
        UserEntity userEntity = userRepository.getByLogin(login);
        List<Map<String, Object>> results = userRepository.allRoles(login);
        results.stream().map(RoleDTO::new)
                .collect(Collectors.toList());

        model.addAttribute("userEntity", userEntity);
        model.addAttribute("userRoles", results);
    }

    @Override
    public void updateUser(UserEntity userEntity, BindingResult result, Model model) {
        try {
            UserEntity newUserEntity = userRepository.getById(userEntity.getId());
            newUserEntity.setFirstName(userEntity.getFirstName());
            newUserEntity.setLastName(userEntity.getLastName());
            newUserEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            newUserEntity.setLogin(userEntity.getLogin());
            newUserEntity.setEmail(userEntity.getEmail());
            userRepository.save(newUserEntity);
        } catch (Exception e) {
            e.getMessage();
        } finally {
            model.addAttribute("users", userRepository.findAll());
        }
    }

    @Override
    public void saveChanges(UserEntity userEntity, BindingResult result, Model model) {
        updateUser(userEntity, result, model);
        model.addAttribute("userEntity", userEntity);
    }

    @Override
    public void deleteUser(String login, Model model) {
        UserEntity userEntity = userRepository.getByLogin(login);
        userRepository.delete(userEntity);
        model.addAttribute("users", userRepository.findAll());
    }

    @Override
    public void all(Model model) {
        model.addAttribute("users", userRepository.findAll());
    }

    @Override
    public void addRole(String login, String role, Model model) {
        UserEntity userEntity = userRepository.getByLogin(login);
        RoleEntity roleEntity = roleRepository.findByName(role);
        UserRoleEntity userRoleEntity = userRoleRepository.findByUserIdAndRoleId(userEntity.getId(), roleEntity.getId());
        if (userRoleEntity == null) {
            UserRoleEntity newUserRoleEntity = new UserRoleEntity();
            newUserRoleEntity.setUserId(userEntity.getId());
            newUserRoleEntity.setRoleId(roleEntity.getId());
            userRoleRepository.save(newUserRoleEntity);
            model.addAttribute("userEntity", userEntity);
            prepareModelForEditRoleForm(login, model);
        }
        model.addAttribute("userEntity", userEntity);
    }

    @Override
    public void deleteRole(String login, String role, Model model) {
        UserEntity userEntity = userRepository.getByLogin(login);
        userRepository.deleteRole(login, role);
        prepareModelForEditRoleForm(login, model);
        model.addAttribute("userEntity", userEntity);
    }
}
