package com.samsolutions.recipes.repositories;

import com.samsolutions.recipes.models.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author kaminskiy.alexey
 * @since 2019.10
 */
@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer> {

    UserEntity getById(UUID uuid);

    UserEntity getByLogin(String login);

    Page<UserEntity> findAll(Pageable pageable);

}
