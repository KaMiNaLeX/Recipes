package com.samsolutions.recipes.repository;

import com.samsolutions.recipes.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * User repository.
 *
 * @author kaminskiy.alexey
 * @since 2019.10
 */
@Repository
public interface UserRepository extends CrudRepository<UserEntity, UUID> {

    UserEntity getById(UUID uuid);

    UserEntity getByLogin(String login);

    Page<UserEntity> findAll(Pageable pageable);

}
