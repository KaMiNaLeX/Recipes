package com.samsolutions.recipes.service.impl;

import com.samsolutions.recipes.dto.FavoriteDTO;
import com.samsolutions.recipes.dto.RecipeDTO;
import com.samsolutions.recipes.dto.createFavorite.CreateFavoriteDTO;
import com.samsolutions.recipes.dto.createRecipe.CreateRecipeDTO;
import com.samsolutions.recipes.model.FavoriteEntity;
import com.samsolutions.recipes.model.RecipeVotesEntity;
import com.samsolutions.recipes.model.UserEntity;
import com.samsolutions.recipes.repository.FavoriteRepository;
import com.samsolutions.recipes.repository.RecipeVotesRepository;
import com.samsolutions.recipes.repository.UserRepository;
import com.samsolutions.recipes.service.FavoriteService;
import com.samsolutions.recipes.service.ModelMapperService;
import com.samsolutions.recipes.service.RecipeService;
import com.samsolutions.recipes.service.RecipeVotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
public class FavoriteServiceImpl extends ModelMapperService implements FavoriteService {
    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecipeVotesRepository recipeVotesRepository;

    @Override
    @Transactional
    public void removeById(UUID uuid) {
        FavoriteEntity favoriteEntity = favoriteRepository.getById(uuid);
        favoriteRepository.delete(favoriteEntity);
    }

    @Override
    public void removeByUserIdAndRecipeId(UUID userId, UUID recipeId) {
        FavoriteEntity favoriteEntity = favoriteRepository.getByUserIdAndRecipeId(userId, recipeId);
        favoriteRepository.delete(favoriteEntity);
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
    public List<FavoriteDTO> findAllByUserId(UUID uuid, int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<FavoriteEntity> favoriteEntityList = favoriteRepository.findAllByUserId(uuid, pageable);
        List<FavoriteDTO> favoriteDTOList = new ArrayList<>();
        for (FavoriteEntity favoriteEntity : favoriteEntityList) {
            RecipeDTO recipeDTO = recipeService.findByRecipeId(favoriteEntity.getRecipeId());
            UserEntity userEntity = userRepository.getById(recipeDTO.getAuthorId());
            recipeDTO.setAuthorName(userEntity.getLogin());
            recipeDTO = checkVotes(uuid, recipeDTO);
            FavoriteDTO favoriteDTO = new FavoriteDTO();
            favoriteDTO.setRecipeDTO(recipeDTO);
            favoriteDTO.setUuid(favoriteEntity.getId());
            favoriteDTO.setAddedAt(favoriteEntity.getAddedAt());
            favoriteDTOList.add(favoriteDTO);
        }

        if (favoriteDTOList.size() == 0) {
            map(favoriteEntityList, FavoriteDTO.class);
        }
        return favoriteDTOList;
    }

    @Override
    public int getCountAllFavoritesRecipes(UUID userId) {
        return favoriteRepository.findAllByUserId(userId).size();
    }

    @Override
    public List<RecipeDTO> checkInFavorite(UUID userId, List<RecipeDTO> list) {
        List<FavoriteEntity> favorites = favoriteRepository.findAllByUserId(userId);
        for (FavoriteEntity favorite : favorites) {
            for (RecipeDTO dto : list) {
                if (favorite.getRecipeId().equals(dto.getId())) {
                    dto.setInFavorite(true);
                }
            }
        }
        return list;
    }

    @Override
    public CreateRecipeDTO checkInFavorite(UUID userId, CreateRecipeDTO recipeDTO) {
        List<FavoriteEntity> favorites = favoriteRepository.findAllByUserId(userId);
        for (FavoriteEntity favorite : favorites) {
            if (favorite.getRecipeId().equals(recipeDTO.getId())) {
                recipeDTO.setInFavorite(true);
            }
        }
        return recipeDTO;
    }

    @Override
    public RecipeDTO checkVotes(UUID userId, RecipeDTO recipeDTO) {
        RecipeVotesEntity recipeVotesEntity = recipeVotesRepository.getByUserIdAndRecipeId(userId, recipeDTO.getId());
        if (recipeVotesEntity != null) {
            if (recipeVotesEntity.isPositiveVote()) {
                recipeDTO.setPositiveVote(true);
            } else recipeDTO.setNegativeVote(true);
        }
        return recipeDTO;
    }
}
