package com.samsolutions.recipes.service.impl;

import com.samsolutions.recipes.dto.IngredientDTO;
import com.samsolutions.recipes.model.Enum.Type;
import com.samsolutions.recipes.model.IngredientEntity;
import com.samsolutions.recipes.model.RecipeIngredientEntity;
import com.samsolutions.recipes.repository.IngredientRepository;
import com.samsolutions.recipes.repository.RecipeIngredientRepository;
import com.samsolutions.recipes.service.IngredientService;
import com.samsolutions.recipes.service.ModelMapperService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/**
 * @author kaminskiy.alexey
 * @since 2019.11
 */
@Log4j2
@Service
public class IngredientServiceImpl extends ModelMapperService implements IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private RecipeIngredientRepository recipeIngredientRepository;

    @Override
    public IngredientDTO createIngredient(IngredientDTO ingredient) {
        IngredientEntity saveIngredient = new IngredientEntity();
        map(ingredient, saveIngredient);
        map(ingredientRepository.save(saveIngredient), ingredient);
        return ingredient;
    }

    @Override
    public List<IngredientDTO> findAll() {
        List<IngredientEntity> sortedList = ingredientRepository.findAll();
        sortedList.sort(new Comparator<IngredientEntity>() {
            @Override
            public int compare(IngredientEntity o1, IngredientEntity o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        return mapListLambda(sortedList, IngredientDTO.class);
    }

    @Override
    @Transactional
    public IngredientDTO updateIngredient(UUID uuid, IngredientDTO ingredient) {
        IngredientEntity updateIngredient = ingredientRepository.getById(uuid);
        ingredient.setId(updateIngredient.getId());
        map(ingredient, updateIngredient);
        map(ingredientRepository.save(updateIngredient), ingredient);
        return ingredient;
    }

    @Override
    @Transactional
    public boolean removeById(UUID uuid) {
        List<RecipeIngredientEntity> recipeIngredientEntityList = recipeIngredientRepository.findAllByIngredientId(uuid);
        if (recipeIngredientEntityList.size() == 0) {
            IngredientEntity removeIngredient = ingredientRepository.getById(uuid);
            ingredientRepository.delete(removeIngredient);
            log.info("Remove ingredient " + uuid + " is successful");
            return true;
        } else {
            log.info("Remove ingredient is failed, because it's used");
            return false;
        }
    }

    @Override
    public IngredientDTO getById(UUID uuid) {
        IngredientDTO ingredientDTO = new IngredientDTO();
        map(ingredientRepository.getById(uuid), ingredientDTO);
        return ingredientDTO;
    }

    @Override
    public IngredientDTO getByName(String name) {
        IngredientDTO ingredientDTO = new IngredientDTO();
        map(ingredientRepository.getByName(name), ingredientDTO);
        return ingredientDTO;
    }

    @Override
    public List<IngredientDTO> findByType(Type type) {
        List<IngredientEntity> sortedList;
        if (type.name().matches("^[A-Za-z\\s]+$")) {
            sortedList = ingredientRepository.findAllByType(type);
        } else sortedList = ingredientRepository.findAllByTypeRu(type);
        sortedList.sort(new Comparator<IngredientEntity>() {
            @Override
            public int compare(IngredientEntity o1, IngredientEntity o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        return mapListLambda(sortedList, IngredientDTO.class);
    }

    @Override
    public List<IngredientDTO> findAll(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<IngredientEntity> entityPage = ingredientRepository.findAll(pageable);
        return mapListLambda(entityPage.getContent(), IngredientDTO.class);
    }
}
