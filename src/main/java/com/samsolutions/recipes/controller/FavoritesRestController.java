package com.samsolutions.recipes.controller;

import com.samsolutions.recipes.dto.FavoriteDTO;
import com.samsolutions.recipes.dto.createFavorite.CreateFavoriteDTO;
import com.samsolutions.recipes.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * @author kaminskiy.alexey
 * @since 2019.12
 */
@RestController
@RequestMapping("/api/favorite")
public class FavoritesRestController {

    @Autowired
    private FavoriteService favoriteService;

    @PostMapping("/create")
    public FavoriteDTO create(@RequestBody CreateFavoriteDTO favoriteDTO) {
        return favoriteService.createDTO(favoriteDTO);
    }

    @DeleteMapping("/delete/{id}")
    public void removeById(@PathVariable("id") UUID uuid) {
        favoriteService.removeById(uuid);
    }

    @GetMapping("/{id}")
    public List<FavoriteDTO> findAllByUserId(@PathVariable("id") UUID uuid) {
        return favoriteService.findAllByUserId(uuid);
    }
}
