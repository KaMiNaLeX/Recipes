package com.samsolutions.recipes.service.impl;

import com.samsolutions.recipes.dto.CategoryDTO;
import com.samsolutions.recipes.dto.createRecipe.CategoryRecipeDTO;
import com.samsolutions.recipes.exception.NotFoundException;
import com.samsolutions.recipes.model.CategoryEntity;
import com.samsolutions.recipes.repository.CategoryRepository;
import com.samsolutions.recipes.service.CategoryService;
import com.samsolutions.recipes.service.FileStorageService;
import com.samsolutions.recipes.service.ModelMapperService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
public class CategoryServiceImpl extends ModelMapperService implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FileStorageService fileStorageService;

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
        categoryDTO.setId(uuid);
        map(categoryDTO, updateCategory);
        map(categoryRepository.save(updateCategory), categoryDTO);
        return categoryDTO;
    }

    @Override
    @Transactional
    public void removeById(UUID uuid) {
        CategoryEntity categoryEntity = categoryRepository.getById(uuid);
        categoryRepository.delete(categoryEntity);
    }

    @Override
    public List<CategoryDTO> findAll() {
        return mapListLambda(categoryRepository.findAll(), CategoryDTO.class);
    }

    @Override
    public List<CategoryRecipeDTO> findAllCategoriesDTO() {
        return mapListLambda(categoryRepository.findAll(), CategoryRecipeDTO.class);
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
        try {
            if (name.matches("^[А-Яа-я\\s]+$")) {
                map(categoryRepository.getByNameRu(name), categoryDTO);
            } else {
                map(categoryRepository.getByName(name), categoryDTO);
            }
            return categoryDTO;
        } catch (NotFoundException e) {
            e.getMessage();
            return null;
        }
    }

    @Override
    public CategoryDTO savePhoto(UUID id, MultipartFile file) throws IOException {
        CategoryEntity categoryEntity = categoryRepository.getById(id);
        String imageSrc = fileStorageService.storeFile(file);
        categoryEntity.setImgSource(imageSrc);
        CategoryDTO categoryDTO = new CategoryDTO();
        map(categoryRepository.save(categoryEntity), categoryDTO);
        return categoryDTO;
    }

    @Override
    public List<CategoryEntity> findAllEntities() {
        return categoryRepository.findAll();
    }

    @Override
    public List<CategoryDTO> findAll(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<CategoryEntity> pageEntity = categoryRepository.findAll(pageable);
        return mapListLambda(pageEntity.getContent(), CategoryDTO.class);
    }

}
