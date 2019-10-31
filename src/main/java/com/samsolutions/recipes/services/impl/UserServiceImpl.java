package com.samsolutions.recipes.services.impl;

import com.samsolutions.recipes.DTO.UserDTO;
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
        List<UserDTO> userDTOList = new ArrayList<>();
        map(userRepository.findAllById(id), userDTOList);
        return userDTOList;
    }

    @Override
    public List<UserDTO> findAll() {
        List<UserDTO> userDTOList = new ArrayList<>();
        UserDTO userDto = new UserDTO();
        userDTOList.add(userDto);
        map(userRepository.findAll(), userDTOList);
        return userDTOList;
    }
}
