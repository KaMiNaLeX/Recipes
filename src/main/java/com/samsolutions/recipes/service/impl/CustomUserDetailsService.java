package com.samsolutions.recipes.service.impl;

import com.samsolutions.recipes.dto.RegistrationUserDTO;
import com.samsolutions.recipes.model.Enum.RoleName;
import com.samsolutions.recipes.model.UserEntity;
import com.samsolutions.recipes.model.UserRoleEntity;
import com.samsolutions.recipes.repository.RoleRepository;
import com.samsolutions.recipes.repository.UserRepository;
import com.samsolutions.recipes.repository.UserRoleRepository;
import com.samsolutions.recipes.service.ModelMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author kaminskiy.alexey
 * @since 2019.12
 */

@Service
public class CustomUserDetailsService implements UserDetailsService, ModelMapperService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    public UserEntity findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserEntity getByLogin(String login) {
        return userRepository.getByLogin(login);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email);
        if (user != null) {
            List<GrantedAuthority> authorities = getUserAuthority(user.getUserRoles());
            return buildUserForAuthentication(user, authorities);
        } else {
            throw new UsernameNotFoundException("username not found");
        }
    }

    private List<GrantedAuthority> getUserAuthority(List<UserRoleEntity> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        userRoles.forEach((role) -> roles.add(new SimpleGrantedAuthority(role.getRole().toString())));

        return new ArrayList<>(roles);
    }

    private UserDetails buildUserForAuthentication(UserEntity user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    @Transactional
    public void saveUser(RegistrationUserDTO user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        UserEntity saveUser = new UserEntity();
        map(user, saveUser);
        map(userRepository.save(saveUser), user);

        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setUserId(saveUser.getId());
        userRoleEntity.setRoleId(roleRepository.findByName(RoleName.VIEWER).getId());
        userRoleRepository.save(userRoleEntity);

        if (user.isAuthor()) {
            UserRoleEntity userRoleEntity2 = new UserRoleEntity();
            userRoleEntity2.setUserId(saveUser.getId());
            userRoleEntity2.setRoleId(roleRepository.findByName(RoleName.AUTHOR).getId());
            userRoleRepository.save(userRoleEntity2);
        }

    }
}
