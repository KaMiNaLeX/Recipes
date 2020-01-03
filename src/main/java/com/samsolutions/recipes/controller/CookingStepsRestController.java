package com.samsolutions.recipes.controller;

import com.samsolutions.recipes.dto.CookingStepDTO;
import com.samsolutions.recipes.model.CookingStepsEntity;
import com.samsolutions.recipes.service.CookingStepsService;
import com.samsolutions.recipes.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.ok;

/**
 * @author kaminskiy.alexey
 * @since 2019.11
 */
@RestController
@RequestMapping("/api/step")
public class CookingStepsRestController {
    @Autowired
    private CookingStepsService cookingStepsService;

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/create")
    public CookingStepsEntity createStep(@RequestBody CookingStepsEntity step) throws IOException {
        return cookingStepsService.createStep(step);
    }

    //todo:need to fix
    @PostMapping(value = "/addPhoto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity createPhoto4Step(@PathParam("id") UUID id, @RequestParam MultipartFile file) throws IOException {
        String imageSrc = fileStorageService.storeFile(file);
        Map<Object, Object> model = new HashMap<>();
        model.put("imgSrc", imageSrc);
        return ok(model);
    }


    @PostMapping("/DTO/create")
    public CookingStepDTO createStepDTO(@RequestBody CookingStepDTO step) throws IOException {
        return cookingStepsService.createStepDTO(step);
    }

    @GetMapping("/")
    public List<CookingStepsEntity> findAll() {
        return cookingStepsService.findAll();
    }

    @GetMapping("/{id}")
    public CookingStepsEntity getById(@PathVariable("id") UUID uuid) {
        return cookingStepsService.getById(uuid);
    }

    @DeleteMapping("/delete/{id}")
    public void removeById(@PathVariable("id") UUID uuid) {
        cookingStepsService.removeStepById(uuid);
    }

    @PutMapping("update/{id}")
    public CookingStepsEntity updateStep(@PathVariable("id") UUID uuid, @RequestBody CookingStepsEntity step)
            throws IOException {
        return cookingStepsService.updateStep(uuid, step);
    }
}
