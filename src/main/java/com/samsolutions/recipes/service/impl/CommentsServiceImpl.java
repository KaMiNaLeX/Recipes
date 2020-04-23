package com.samsolutions.recipes.service.impl;

import com.samsolutions.recipes.dto.CommentsDTO;
import com.samsolutions.recipes.model.CommentsEntity;
import com.samsolutions.recipes.repository.CommentsRepository;
import com.samsolutions.recipes.service.CommentsService;
import com.samsolutions.recipes.service.ModelMapperService;
import com.samsolutions.recipes.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Kaminskiy Alexey
 * @since 2020.03
 **/
@Log4j2
@Service
public class CommentsServiceImpl extends ModelMapperService implements CommentsService {
    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private UserService userService;

    @Override
    public CommentsDTO create(CommentsDTO commentsDTO) {
        commentsDTO.setCreationDate(new Date());
        commentsDTO.setUpdateDate(new Date());
        CommentsEntity commentsEntity = new CommentsEntity();
        map(commentsDTO, commentsEntity);
        commentsRepository.save(commentsEntity);
        commentsDTO.setId(commentsEntity.getId());
        commentsDTO.setCreatorName(userService.getById(commentsEntity.getCreatorId()).getLogin());
        return commentsDTO;
    }

    @Override
    public List<CommentsDTO> findAll(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<CommentsEntity> pageEntity = commentsRepository.findAll(pageable);
        List<CommentsDTO> result = mapListLambda(pageEntity.getContent(), CommentsDTO.class);
        if (result != null) {
            for (CommentsDTO commentsDTO : result) {
                commentsDTO.setCreatorName(userService.getById(commentsDTO.getCreatorId()).getLogin());
            }
        }
        return result;
    }

    @Override
    public CommentsDTO getById(UUID uuid) {
        CommentsDTO commentsDTO = new CommentsDTO();
        CommentsEntity commentsEntity = commentsRepository.getById(uuid);
        map(commentsEntity, commentsDTO);
        commentsDTO.setCreatorName(userService.getById(commentsEntity.getCreatorId()).getLogin());
        return commentsDTO;
    }

    @Override
    public CommentsDTO update(UUID uuid, CommentsDTO commentsDTO) {
        CommentsEntity commentsEntity = commentsRepository.getById(uuid);
        commentsDTO.setId(uuid);
        commentsDTO.setUpdateDate(new Date());
        commentsDTO.setCreationDate(commentsEntity.getCreationDate());
        commentsDTO.setCreatorName(userService.getById(commentsEntity.getCreatorId()).getLogin());
        map(commentsDTO, commentsEntity);
        map(commentsRepository.save(commentsEntity), commentsDTO);
        return commentsDTO;
    }

    @Override
    public void delete(UUID uuid) {
        CommentsEntity commentsEntity = commentsRepository.getById(uuid);
        commentsRepository.delete(commentsEntity);
    }

    @Override
    public List<CommentsDTO> findAllByRecipeId(UUID recipeId, int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<CommentsEntity> pageEntity = commentsRepository.findAllByRecipeId(recipeId, pageable);
        List<CommentsDTO> result = mapListLambda(pageEntity.getContent(), CommentsDTO.class);
        if (result != null) {
            for (CommentsDTO commentsDTO : result) {
                commentsDTO.setCreatorName(userService.getById(commentsDTO.getCreatorId()).getLogin());
            }
        }
        return result;
    }

    @Override
    public int getCountAllComments(UUID recipeId) {
        return commentsRepository.findAllByRecipeId(recipeId).size();
    }
}
