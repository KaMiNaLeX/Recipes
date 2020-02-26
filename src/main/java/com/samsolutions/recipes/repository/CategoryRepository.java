package com.samsolutions.recipes.repository;

import com.samsolutions.recipes.model.CategoryEntity;
import org.springframework.stereotype.Repository;

/**
 * @author kaminskiy.alexey
 * @since 2019.11
 */
@Repository
public interface CategoryRepository extends BaseRepository<CategoryEntity> {
    CategoryEntity getByName(String name);
}
