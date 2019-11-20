package com.samsolutions.recipes.service.impl;

import com.samsolutions.recipes.model.CategoryEntity;
import com.samsolutions.recipes.repository.CategoryRepository;
import com.samsolutions.recipes.service.CategoryService;
import com.samsolutions.recipes.service.ModelMapperService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * The service has been annotated with @Service so that Spring Boot will detect it and
 * create an instance of it.
 *
 * @author kaminskiy.alexey
 * @since 2019.11
 */

@Log4j2
@Service
public class CategoryServiceImpl implements CategoryService, ModelMapperService {
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public CategoryEntity createCategory(CategoryEntity categoryEntity) {
        CategoryEntity newCategory = new CategoryEntity();
        newCategory.setName(categoryEntity.getName());
        newCategory.setDescription(categoryEntity.getDescription());
        newCategory.setTag(categoryEntity.getTag());
        categoryRepository.save(newCategory);
        return newCategory;
    }

    @Override
    public CategoryEntity updateCategory(UUID uuid, CategoryEntity categoryEntity) {
        CategoryEntity updateCategory = categoryRepository.getById(uuid);
        updateCategory.setName(categoryEntity.getName());
        updateCategory.setDescription(categoryEntity.getDescription());
        updateCategory.setTag(categoryEntity.getTag());
        categoryRepository.save(updateCategory);
        return updateCategory;
    }

    @Override
    public void removeById(UUID uuid) {
        CategoryEntity categoryEntity = categoryRepository.getById(uuid);
        categoryRepository.delete(categoryEntity);
    }

    @Override
    public List<CategoryEntity> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public CategoryEntity getById(UUID uuid) {
        return categoryRepository.getById(uuid);
    }

}
