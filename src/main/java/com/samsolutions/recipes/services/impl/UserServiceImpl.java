package com.samsolutions.recipes.services.impl;

import com.samsolutions.recipes.DTO.UserDto;
import com.samsolutions.recipes.models.UserEntity;
import com.samsolutions.recipes.repositories.UserRepository;
import com.samsolutions.recipes.services.ModelMapperService;
import com.samsolutions.recipes.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * @author kaminskiy.alexey
 * @since 2019.10
 */
@Service
public class UserServiceImpl implements UserService, ModelMapperService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List findAllById(int id) {
        List<UserDto> userDtoList = new ArrayList<>();
        map(userRepository.findAllById(id), userDtoList);
        return userDtoList;
    }

    @Override
    public List<UserDto> findAll() {
        List<UserDto> userDtoList = new ArrayList<>();
        UserDto userDto = new UserDto();
        userDtoList.add(userDto);
        map(userRepository.findAll(), userDtoList);
        return userDtoList;
    }
}
