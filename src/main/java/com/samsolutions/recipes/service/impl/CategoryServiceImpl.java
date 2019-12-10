package com.samsolutions.recipes.service.impl;

import com.samsolutions.recipes.dto.CategoryDTO;
import com.samsolutions.recipes.model.CategoryEntity;
import com.samsolutions.recipes.repository.CategoryRepository;
import com.samsolutions.recipes.service.CategoryService;
import com.samsolutions.recipes.service.ModelMapperService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    private CategoryRepository categoryRepository;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity = new CategoryEntity();
        map(categoryDTO, categoryEntity);
        map(categoryRepository.save(categoryEntity), categoryDTO);
        return categoryDTO;
    }

    @Override
    @Transactional
    public CategoryDTO updateCategory(UUID uuid, CategoryDTO categoryDTO) {
        CategoryEntity updateCategory = categoryRepository.getById(uuid);
        CategoryDTO updateCategoryDTO = new CategoryDTO();
        updateCategoryDTO.setName(categoryDTO.getName());
        updateCategoryDTO.setDescription(categoryDTO.getDescription());
        updateCategoryDTO.setTag(categoryDTO.getTag());
        updateCategoryDTO.setId(updateCategory.getId());
        map(updateCategoryDTO, updateCategory);
        map(categoryRepository.save(updateCategory), updateCategoryDTO);
        return updateCategoryDTO;
    }

    @Override
    @Transactional
    public void removeById(UUID uuid) {
        CategoryEntity categoryEntity = categoryRepository.getById(uuid);
        categoryRepository.delete(categoryEntity);
    }

    @Override
    public List<CategoryDTO> findAll() {
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTOList.add(categoryDTO);
        map(categoryRepository.findAll(), categoryDTOList);
        return categoryDTOList;
    }

    @Override
    public CategoryDTO getById(UUID uuid) {
        CategoryDTO categoryDTO = new CategoryDTO();
        map(categoryRepository.getById(uuid), categoryDTO);
        return categoryDTO;
    }

    @Override
    public CategoryDTO getByName(String name) {
        CategoryDTO categoryDTO = new CategoryDTO();
        map(categoryRepository.getByName(name), categoryDTO);
        return categoryDTO;
    }

}
