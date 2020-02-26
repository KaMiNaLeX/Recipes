package com.samsolutions.recipes.controller;

import com.samsolutions.recipes.dto.FavoriteDTO;
import com.samsolutions.recipes.dto.createFavorite.CreateFavoriteDTO;
import com.samsolutions.recipes.exception.CustomGlobalExceptionHandler;
import com.samsolutions.recipes.service.FavoriteService;
import com.samsolutions.recipes.service.validation.ValidUUID;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

/**
 * @author kaminskiy.alexey
 * @since 2019.12
 */
@Log4j2
@Validated
@RestController
@RequestMapping("/api/favorite")
public class FavoritesRestController extends CustomGlobalExceptionHandler {

    @Autowired
    private FavoriteService favoriteService;

    @PostMapping("/create")
    public FavoriteDTO create(@Valid @RequestBody CreateFavoriteDTO favoriteDTO) {
        try {
            log.info("Create favorite is successful");
            return favoriteService.createDTO(favoriteDTO);
        } catch (Exception e) {
            log.error("Create favorite is failed", e.getCause());
            return null;
        }
    }

    @DeleteMapping("/delete/{id}")
    public void removeById(@PathVariable("id") @ValidUUID UUID uuid) {
        favoriteService.removeById(uuid);
        log.info("Remove favorite " + uuid + " is successful");
    }

    @GetMapping("/{id}")
    public List<FavoriteDTO> findAllByUserId(@PathVariable("id") @ValidUUID UUID uuid, @RequestParam("page") int page,
                                             @RequestParam("size") int size, @RequestParam("sort") String sort) {
        return favoriteService.findAllByUserId(uuid, page, size, sort);
    }
}
