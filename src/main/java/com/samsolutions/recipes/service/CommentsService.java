package com.samsolutions.recipes.service;

import com.samsolutions.recipes.dto.CommentsDTO;

import java.util.List;
import java.util.UUID;

/**
 * @author Kaminskiy Alexey
 * @since 2020.03
 **/
public interface CommentsService {
    CommentsDTO create(CommentsDTO commentsDTO);

    List<CommentsDTO> findAll(int page, int size, String sort);

    CommentsDTO getById(UUID uuid);

    CommentsDTO update(UUID uuid, CommentsDTO commentsDTO);

    void delete(UUID uuid);
}
