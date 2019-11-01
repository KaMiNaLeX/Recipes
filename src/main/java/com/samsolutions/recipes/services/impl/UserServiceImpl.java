package com.samsolutions.recipes.services.impl;

import com.samsolutions.recipes.DTO.UserDTO;
import com.samsolutions.recipes.models.UserEntity;
import com.samsolutions.recipes.repositories.UserRepository;
import com.samsolutions.recipes.services.ModelMapperService;
import com.samsolutions.recipes.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * @author kaminskiy.alexey
 * @since 2019.10
 */
@Service
public class UserServiceImpl implements UserService, ModelMapperService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public UserEntity getById(UUID uuid) {
        UserEntity userEntity = userRepository.getById(uuid);
        return userEntity;
    }

    @Override
    public UserEntity getByLogin(String login) {
        UserEntity userEntity = userRepository.getByLogin(login);
        return userEntity;
    }


    @Override
    public void removeByLogin(String login) {
        UserEntity userEntity = userRepository.getByLogin(login);
        userRepository.delete(userEntity);
    }

    @Override
    public UserEntity createUser(UserEntity userEntity) {
        UserEntity newUserEntity = new UserEntity();
        newUserEntity.setFirstName(userEntity.getFirstName());
        newUserEntity.setLastName(userEntity.getLastName());
        newUserEntity.setEmail(userEntity.getEmail());
        newUserEntity.setLogin(userEntity.getLogin());
        newUserEntity.setPassword(userEntity.getPassword());
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
}
