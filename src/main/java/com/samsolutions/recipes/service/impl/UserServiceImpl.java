package com.samsolutions.recipes.service.impl;

import com.samsolutions.recipes.dto.RoleDTO;
import com.samsolutions.recipes.dto.UserDTO;
import com.samsolutions.recipes.model.Enum.RoleName;
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
import org.springframework.transaction.annotation.Transactional;
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
    public UserDTO getById(UUID uuid) {
        UserEntity userEntity = userRepository.getById(uuid);
        UserDTO userDTO = new UserDTO();
        map(userEntity, userDTO);
        return userDTO;
    }

    @Override
    public UserDTO getByLogin(String login) {
        UserEntity userEntity = userRepository.getByLogin(login);
        UserDTO userDTO = new UserDTO();
        map(userEntity, userDTO);
        return userDTO;
    }


    @Override
    @Transactional
    public void removeByLogin(String login) {
        UserEntity userEntity = userRepository.getByLogin(login);
        userRepository.delete(userEntity);
    }

    @Override
    @Transactional
    public void removeById(UUID uuid) {
        UserEntity userEntity = userRepository.getById(uuid);
        userRepository.delete(userEntity);
    }

    @Override
    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        UserEntity saveUser = new UserEntity();
        map(userDTO, saveUser);
        saveUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        map(userRepository.save(saveUser), userDTO);

        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setUserId(saveUser.getId());
        userRoleEntity.setRoleId(roleRepository.findByName(RoleName.VIEWER).getId());
        userRoleRepository.save(userRoleEntity);
        return userDTO;
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
    @Transactional
    public UserDTO updateUser(UUID uuid, UserDTO userDTO) {
        UserEntity newUserEntity = userRepository.getById(uuid);
        map(userDTO, newUserEntity);
        map(userRepository.save(newUserEntity), userDTO);
        return userDTO;
    }

    @Override
    public UserDTO getByEmail(String email) {
        UserEntity userEntity = userRepository.getByEmail(email);
        UserDTO userDTO = new UserDTO();
        map(userEntity, userDTO);
        return userDTO;
    }

    @Override
    public Boolean checkPassword(UUID uuid, String pass) {
        UserEntity userEntity = userRepository.getById(uuid);
        return passwordEncoder.matches(pass, userEntity.getPassword());
    }

    @Override
    @Transactional
    public void savePassword(UUID uuid, String pass) {
        UserEntity userEntity = userRepository.getById(uuid);
        userEntity.setPassword(passwordEncoder.encode(pass));
        userRepository.save(userEntity);
    }

}
