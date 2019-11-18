package com.samsolutions.recipes.repository;

import com.samsolutions.recipes.model.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author kaminskiy.alexey
 * @since 2019.11
 */
@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, UUID>, BaseRepository<CategoryEntity> {
}
