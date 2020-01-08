package com.samsolutions.recipes.service.impl;

import com.samsolutions.recipes.dto.FavoriteDTO;
import com.samsolutions.recipes.dto.RecipeDTO;
import com.samsolutions.recipes.dto.createFavorite.CreateFavoriteDTO;
import com.samsolutions.recipes.model.FavoriteEntity;
import com.samsolutions.recipes.repository.FavoriteRepository;
import com.samsolutions.recipes.service.FavoriteService;
import com.samsolutions.recipes.service.ModelMapperService;
import com.samsolutions.recipes.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author kaminskiy.alexey
 * @since 2019.12
 */
@Service
public class FavoriteServiceImpl implements FavoriteService, ModelMapperService {
    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private RecipeService recipeService;

    @Override
    @Transactional
    public void removeById(UUID uuid) {
        FavoriteEntity favoriteEntity = favoriteRepository.getById(uuid);
        favoriteRepository.removeById(favoriteEntity.getId());
    }

    @Override
    @Transactional
    public FavoriteDTO createDTO(CreateFavoriteDTO createFavoriteDTO) {
        FavoriteEntity createEntity = new FavoriteEntity();
        FavoriteDTO favoriteDTO = new FavoriteDTO();
        RecipeDTO recipeDTO = recipeService.findByRecipeId(createFavoriteDTO.getRecipeId());
        favoriteDTO.setRecipeDTO(recipeDTO);
        createEntity.setRecipeId(createFavoriteDTO.getRecipeId());
        createEntity.setUserId(createFavoriteDTO.getUserId());
        map(favoriteRepository.save(createEntity), favoriteDTO.getRecipeDTO());
        return favoriteDTO;
    }

    @Override
    @Transactional
    public List<FavoriteDTO> findAllByUserId(UUID uuid) {
        List<FavoriteEntity> favoriteEntityList = favoriteRepository.findAllByUserId(uuid);
        List<FavoriteDTO> favoriteDTOList = new ArrayList<>();
        for (int i = 0; i < favoriteEntityList.size(); i++) {
            RecipeDTO recipeDTO = recipeService.findByRecipeId(favoriteEntityList.get(i).getRecipeId());
            FavoriteDTO favoriteDTO = new FavoriteDTO();
            favoriteDTO.setRecipeDTO(recipeDTO);
            favoriteDTO.setAddedAt(favoriteEntityList.get(i).getAddedAt());
            favoriteDTOList.add(favoriteDTO);
        }

        if (favoriteDTOList.size() == 0) {
            FavoriteDTO favoriteDTO = new FavoriteDTO();
            favoriteDTOList.add(favoriteDTO);
            map(favoriteEntityList, favoriteDTOList);
        }
        return favoriteDTOList;
    }
}
