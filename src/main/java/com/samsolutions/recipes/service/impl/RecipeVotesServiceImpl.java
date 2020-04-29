package com.samsolutions.recipes.service.impl;

import com.samsolutions.recipes.dto.RecipeVotesDTO;
import com.samsolutions.recipes.model.RecipeVotesEntity;
import com.samsolutions.recipes.repository.RecipeVotesRepository;
import com.samsolutions.recipes.service.ModelMapperService;
import com.samsolutions.recipes.service.RecipeService;
import com.samsolutions.recipes.service.RecipeVotesService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author Kaminskiy Alexey
 * @since 2020.03
 **/
@Log4j2
@Service
public class RecipeVotesServiceImpl extends ModelMapperService implements RecipeVotesService {
    @Autowired
    private RecipeVotesRepository recipeVotesRepository;

    @Autowired
    private RecipeService recipeService;

    @Override
    public RecipeVotesDTO create(RecipeVotesDTO recipeVotesDTO) {
        try {
            RecipeVotesEntity recipeVotesEntity =
                    recipeVotesRepository.getByUserIdAndRecipeId(recipeVotesDTO.getUserId(), recipeVotesDTO.getRecipeId());
            if (recipeVotesEntity.isNegativeVote() == recipeVotesDTO.isNegativeVote()) {
                log.info("Re the voice");
                return null;
            } else if (recipeVotesEntity.isPositiveVote() == recipeVotesDTO.isPositiveVote()) {
                log.info("Re the voice");
                return null;
            } else {
                recipeVotesDTO.setId(recipeVotesEntity.getId());
                if (recipeVotesDTO.isPositiveVote()) {
                    recipeService.positiveVote(recipeVotesEntity.getRecipeId());
                    recipeService.removeNegativeVote(recipeVotesEntity.getRecipeId());
                } else {
                    recipeService.negativeVote(recipeVotesEntity.getRecipeId());
                    recipeService.removePositiveVote(recipeVotesEntity.getRecipeId());
                }
                return update(recipeVotesEntity.getId(), recipeVotesDTO);
            }
        } catch (Exception e) {
            RecipeVotesEntity recipeVotesEntity = new RecipeVotesEntity();
            map(recipeVotesDTO, recipeVotesEntity);
            map(recipeVotesRepository.save(recipeVotesEntity), recipeVotesDTO);
            recipeVotesDTO.setId(recipeVotesEntity.getId());

            if (recipeVotesDTO.isPositiveVote()) {
                recipeService.positiveVote(recipeVotesEntity.getRecipeId());
            } else recipeService.negativeVote(recipeVotesEntity.getRecipeId());

            return recipeVotesDTO;
        }
    }

    @Override
    public RecipeVotesDTO update(UUID uuid, RecipeVotesDTO recipeVotesDTO) {
        RecipeVotesEntity recipeVotesEntity = recipeVotesRepository.getById(uuid);
        map(recipeVotesDTO, recipeVotesEntity);
        map(recipeVotesRepository.save(recipeVotesEntity), recipeVotesDTO);
        log.info("Update recipeVotes " + uuid + "  is successful");
        return recipeVotesDTO;
    }

    @Override
    public void delete(UUID uuid) {
        RecipeVotesEntity recipeVotesEntity = recipeVotesRepository.getById(uuid);
        recipeVotesRepository.delete(recipeVotesEntity);
    }
}
