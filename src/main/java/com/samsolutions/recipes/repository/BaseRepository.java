package com.samsolutions.recipes.repository;

import com.samsolutions.recipes.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.UUID;

/**
 * @author kaminskiy.alexey
 * @since 2019.11
 */
@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity> extends JpaRepository<T, UUID> {
    T getById(UUID uuid);

    T removeById(UUID uuid);
}
