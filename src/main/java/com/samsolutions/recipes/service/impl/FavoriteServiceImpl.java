package com.samsolutions.recipes.service.impl;

import com.samsolutions.recipes.dto.RecipeDTO;
import com.samsolutions.recipes.model.FavoriteEntity;
import com.samsolutions.recipes.repository.FavoriteRepository;
import com.samsolutions.recipes.service.FavoriteService;
import com.samsolutions.recipes.service.ModelMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author kaminskiy.alexey
 * @since 2019.12
 */
@Service
public class FavoriteServiceImpl implements FavoriteService, ModelMapperService {
    @Autowired
    private FavoriteRepository favoriteRepository;

    @Override
    public FavoriteEntity create(FavoriteEntity favoriteEntity) {
        return favoriteRepository.save(favoriteEntity);
    }

    @Override
    @Transactional
    public void removeById(UUID uuid) {
        FavoriteEntity favoriteEntity = favoriteRepository.getById(uuid);
        favoriteRepository.removeById(favoriteEntity.getId());
    }

    //todo: need to fix map and ID parameter
    @Override
    public List<RecipeDTO> getFavoritesById(UUID uuid) {
        List<RecipeDTO> list = new ArrayList<>();
        RecipeDTO recipeDTO = new RecipeDTO();
        list.add(recipeDTO);
        List<Map<String, Object>> results = favoriteRepository.getFavoritesById(uuid);
        results.stream().map(RecipeDTO::new)
                .collect(Collectors.toList());
        map(results, list);
        return list;
    }
}
