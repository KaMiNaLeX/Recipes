package com.samsolutions.recipes.repositories;

import com.samsolutions.recipes.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author kaminskiy.alexey
 * @since 2019.10
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    List findAllById(int id);
}
