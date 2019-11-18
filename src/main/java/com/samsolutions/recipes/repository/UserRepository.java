package com.samsolutions.recipes.repository;

import com.samsolutions.recipes.DTO.RoleDTO;
import com.samsolutions.recipes.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
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

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM user_role WHERE user_id = (select id from USER u where u.login=:LOGIN) AND"
            + " role_id = (select id from ROLE r where r.name=:ROLE)",
            countQuery = "SELECT COUNT(*) FROM USER_ROLE", nativeQuery = true)
    void deleteRole(@Param("LOGIN") String login, @Param("ROLE") String role);

    @Query(value = "SELECT r.name as " + RoleDTO.NAME + ","
            + "r.description as " + RoleDTO.DESCRIPTION
            + " FROM role r "
            + " INNER JOIN user_role ur1 on r.id = ur1.role_id"
            + " INNER JOIN user u on ur1.user_id = u.id WHERE u.login=:LOGIN",
            countQuery = "SELECT COUNT(*) FROM USER", nativeQuery = true)
    List<Map<String, Object>> allRoles(@Param("LOGIN") String login);


}
