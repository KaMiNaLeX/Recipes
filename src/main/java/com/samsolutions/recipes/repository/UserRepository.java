package com.samsolutions.recipes.repository;

import com.samsolutions.recipes.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * User repository.
 *
 * @author kaminskiy.alexey
 * @since 2019.10
 */
@Repository
public interface UserRepository extends BaseRepository<UserEntity> {

    UserEntity findByEmail(String email);

    UserEntity getByLogin(String login);

    UserEntity getByEmail(String email);

    Page<UserEntity> findAll(Pageable pageable);

}
