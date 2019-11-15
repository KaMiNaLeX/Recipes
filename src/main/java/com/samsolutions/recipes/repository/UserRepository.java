package com.samsolutions.recipes.repository;

import com.samsolutions.recipes.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * User repository.
 *
 * @author kaminskiy.alexey
 * @since 2019.10
 */
@Repository
public interface UserRepository extends CrudRepository<UserEntity, UUID>, BaseRepository<UserEntity> {

    UserEntity getByLogin(String login);

    Page<UserEntity> findAll(Pageable pageable);

    UserEntity getByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM user_role WHERE user_id = (select id from USER u where u.login=:LOGIN) AND " +
            "role_id = (select id from ROLE r where r.name=:ROLE)",
            countQuery = "SELECT COUNT(*) FROM USER_ROLE", nativeQuery = true)
    void deleteRole(@Param("LOGIN") String login, @Param("ROLE") String role);

}
