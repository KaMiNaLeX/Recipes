package com.samsolutions.recipes.controller;

import com.samsolutions.recipes.dto.CommentsDTO;
import com.samsolutions.recipes.exception.CustomGlobalExceptionHandler;
import com.samsolutions.recipes.service.CommentsService;
import com.samsolutions.recipes.service.validation.ValidUUID;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

/**
 * @author Kaminskiy Alexey
 * @since 2020.03
 **/
@Log4j2
@Validated
@RestController
@RequestMapping("/api/comments")
public class CommentsRestController extends CustomGlobalExceptionHandler {

    @Autowired
    private CommentsService commentsService;

    @GetMapping("/")
    public List<CommentsDTO> findAll(@RequestParam("page") int page, @RequestParam("size") int size,
                                     @RequestParam("sort") String sort) {
        return commentsService.findAll(page, size, sort);
    }

    @GetMapping("/id/{id}")
    public CommentsDTO getById(@PathVariable("id") UUID uuid) {
        return commentsService.getById(uuid);
    }

    @DeleteMapping("/delete/{id}")
    public void removeById(@PathVariable("id") @ValidUUID UUID uuid) {
        commentsService.delete(uuid);
        log.info("Remove " + uuid + " comment is successful");
    }

    @PostMapping("/create")
    public CommentsDTO create(@Valid @RequestBody CommentsDTO commentsDTO) {
        try {
            log.info("Create comment " + commentsDTO.getId() + " is successful");
            return commentsService.create(commentsDTO);
        } catch (Exception e) {
            log.error("Create comment " + commentsDTO.getId() + " is failed", e.getCause());
            return null;
        }
    }

    @PutMapping("/update/{id}")
    public CommentsDTO update(@PathVariable("id") @ValidUUID UUID uuid,
                              @Valid @RequestBody CommentsDTO commentsDTO) {
        try {
            log.info("Update comment " + uuid + "  is successful");
            return commentsService.update(uuid, commentsDTO);
        } catch (Exception e) {
            log.error("Update comment " + uuid + " is failed", e.getCause());
            return null;
        }
    }
}
