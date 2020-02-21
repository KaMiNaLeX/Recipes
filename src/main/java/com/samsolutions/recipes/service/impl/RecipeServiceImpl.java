package com.samsolutions.recipes.service.impl;

import com.samsolutions.recipes.dto.RecipeDTO;
import com.samsolutions.recipes.dto.UserDTO;
import com.samsolutions.recipes.dto.createRecipe.CategoryRecipeDTO;
import com.samsolutions.recipes.dto.createRecipe.CookingStepRecipeDTO;
import com.samsolutions.recipes.dto.createRecipe.CreateRecipeDTO;
import com.samsolutions.recipes.dto.createRecipe.IngredientRecipeDTO;
import com.samsolutions.recipes.dto.findByData.CookingDifficultyDTO;
import com.samsolutions.recipes.dto.findByData.RecipeDataDTO;
import com.samsolutions.recipes.dto.findByIngredients.IngredientNameListDTO;
import com.samsolutions.recipes.exception.NotFoundException;
import com.samsolutions.recipes.exception.UserNotFoundException;
import com.samsolutions.recipes.model.CategoryEntity;
import com.samsolutions.recipes.model.CategoryRecipeEntity;
import com.samsolutions.recipes.model.CookingStepsEntity;
import com.samsolutions.recipes.model.Enum.CookingDifficulty;
import com.samsolutions.recipes.model.FavoriteEntity;
import com.samsolutions.recipes.model.IngredientEntity;
import com.samsolutions.recipes.model.RecipeEntity;
import com.samsolutions.recipes.model.RecipeIngredientEntity;
import com.samsolutions.recipes.model.UserEntity;
import com.samsolutions.recipes.repository.CategoryRecipeRepository;
import com.samsolutions.recipes.repository.CategoryRepository;
import com.samsolutions.recipes.repository.CookingStepsRepository;
import com.samsolutions.recipes.repository.IngredientRepository;
import com.samsolutions.recipes.repository.RecipeIngredientRepository;
import com.samsolutions.recipes.repository.RecipeRepository;
import com.samsolutions.recipes.repository.UserRepository;
import com.samsolutions.recipes.service.ModelMapperService;
import com.samsolutions.recipes.service.RecipeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * @author kaminskiy.alexey
 * @since 2019.12
 */
@Log4j2
@Service
public class RecipeServiceImpl implements RecipeService, ModelMapperService {
    private final RecipeRepository recipeRepository;

    private final CategoryRepository categoryRepository;

    private final CategoryRecipeRepository categoryRecipeRepository;

    private final CookingStepsRepository cookingStepsRepository;

    private final IngredientRepository ingredientRepository;

    private final RecipeIngredientRepository recipeIngredientRepository;

    private final UserRepository userRepository;

    private final FileStorageServiceImpl fileStorageService;

    public RecipeServiceImpl(RecipeRepository recipeRepository,
                             CategoryRepository categoryRepository,
                             CategoryRecipeRepository categoryRecipeRepository,
                             CookingStepsRepository cookingStepsRepository,
                             IngredientRepository ingredientRepository,
                             RecipeIngredientRepository recipeIngredientRepository,
                             UserRepository userRepository, FileStorageServiceImpl fileStorageService) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.categoryRecipeRepository = categoryRecipeRepository;
        this.cookingStepsRepository = cookingStepsRepository;
        this.ingredientRepository = ingredientRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.userRepository = userRepository;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public List<RecipeDTO> findAll() {
        List<RecipeDTO> recipeDTOList = new ArrayList<>();
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTOList.add(recipeDTO);
        map(recipeRepository.findAll(), recipeDTOList);
        return recipeDTOList;
    }

    @Override
    public RecipeDTO create(RecipeDTO recipeDTO) {
        recipeDTO.setNegativeVotes(0);
        recipeDTO.setPositiveVotes(0);
        RecipeEntity recipeEntity = new RecipeEntity();
        map(recipeDTO, recipeEntity);
        map(recipeRepository.save(recipeEntity), recipeDTO);
        return recipeDTO;
    }

    @Override
    @Transactional
    public RecipeDTO update(UUID uuid, RecipeDTO recipeDTO) {
        RecipeEntity updateEntity = recipeRepository.getById(uuid);
        recipeDTO.setId(updateEntity.getId());
        recipeDTO.setAuthorId(recipeDTO.getAuthorId());
        map(recipeDTO, updateEntity);
        map(recipeRepository.save(updateEntity), recipeDTO);
        return recipeDTO;
    }

    @Override
    public RecipeDTO findByRecipeId(UUID uuid) {
        RecipeDTO recipeDTO = new RecipeDTO();
        map(recipeRepository.getById(uuid), recipeDTO);
        return recipeDTO;
    }

    @Override
    public void updateCategory(CreateRecipeDTO createRecipeDTO, RecipeEntity updateEntity) {
        List<CategoryEntity> categoryEntityList = new ArrayList<>();
        CategoryEntity categoryEntityForMap = new CategoryEntity();
        categoryEntityList.add(categoryEntityForMap);
        map(createRecipeDTO.getCategoryRecipeDTOList(), categoryEntityList);

        List<CategoryRecipeEntity> updateCategoryRecipeList =
                categoryRecipeRepository.findAllByRecipeId(updateEntity.getId());
        for (CategoryRecipeEntity recipeEntity : updateCategoryRecipeList) {
            categoryRecipeRepository.delete(recipeEntity);
        }
        for (int i = 0; i < categoryEntityList.size(); i++) {
            CategoryEntity categoryEntity = categoryRepository.getByName(categoryEntityList.get(i).getName());
            if (updateCategoryRecipeList.size() < categoryEntityList.size()) {
                CategoryRecipeEntity categoryRecipeEntity = new CategoryRecipeEntity();
                categoryRecipeEntity.setRecipeId(updateEntity.getId());
                categoryRecipeEntity.setCategoryId(categoryEntity.getId());
                updateCategoryRecipeList.add(categoryRecipeEntity);
                int lastIndex = updateCategoryRecipeList.size() - 1;
                categoryRecipeRepository.save(updateCategoryRecipeList.get(lastIndex));
            }

            updateCategoryRecipeList.get(i).setCategoryId(categoryEntity.getId());
            updateCategoryRecipeList.get(i).setRecipeId(updateEntity.getId());
            map(categoryRecipeRepository.save(updateCategoryRecipeList.get(i)),
                    createRecipeDTO.getCategoryRecipeDTOList());
        }
    }

    @Override
    public void updateCookingStepsEntityList(CreateRecipeDTO createRecipeDTO, RecipeEntity updateEntity) {
        List<CookingStepsEntity> cookingStepsEntityList = new ArrayList<>();
        CookingStepsEntity cookingStepsEntityForMap = new CookingStepsEntity();
        cookingStepsEntityList.add(cookingStepsEntityForMap);
        map(createRecipeDTO.getCookingStepRecipeDTOList(), cookingStepsEntityList);

        List<CookingStepsEntity> updateCookingStepsList =
                cookingStepsRepository.findAllByRecipeId(updateEntity.getId());
        for (CookingStepsEntity value : updateCookingStepsList) {
            cookingStepsRepository.delete(value);
        }

        for (CookingStepsEntity stepsEntity : cookingStepsEntityList) {
            stepsEntity.setRecipeId(updateEntity.getId());
            map(cookingStepsRepository.save(stepsEntity),
                    createRecipeDTO.getCookingStepRecipeDTOList());
        }
    }

    @Override
    public void updateRecipeIngredientList(CreateRecipeDTO createRecipeDTO, RecipeEntity updateEntity) {
        List<RecipeIngredientEntity> recipeIngredientEntityList = new ArrayList<>();
        RecipeIngredientEntity recipeIngredientEntityForMap = new RecipeIngredientEntity();
        recipeIngredientEntityList.add(recipeIngredientEntityForMap);
        map(createRecipeDTO.getIngredientRecipeDTOList(), recipeIngredientEntityList);

        List<RecipeIngredientEntity> updateRecipeIngredientList =
                recipeIngredientRepository.findAllByRecipeId(updateEntity.getId());
        for (RecipeIngredientEntity entity : updateRecipeIngredientList) {
            recipeIngredientRepository.delete(entity);
        }
        for (int i = 0; i < recipeIngredientEntityList.size(); i++) {
            IngredientEntity ingredientEntity =
                    ingredientRepository.getByName(createRecipeDTO.getIngredientRecipeDTOList().get(i).getName());
            recipeIngredientEntityList.get(i).setIngredientId(ingredientEntity.getId());
            recipeIngredientEntityList.get(i).setRecipeId(updateEntity.getId());
            map(recipeIngredientRepository.save(recipeIngredientEntityList.get(i)),
                    createRecipeDTO.getIngredientRecipeDTOList());
        }
    }

    @Override
    public CreateRecipeDTO updateRecipe(UUID uuid, CreateRecipeDTO createRecipeDTO) {
        RecipeEntity updateEntity = recipeRepository.getById(uuid);
        createRecipeDTO.setId(updateEntity.getId());
        map(createRecipeDTO, updateEntity);
        map(recipeRepository.save(updateEntity), createRecipeDTO);
        //update category
        updateCategory(createRecipeDTO, updateEntity);
        //update CookingStepsEntityList
        updateCookingStepsEntityList(createRecipeDTO, updateEntity);
        //update RecipeIngredientList
        updateRecipeIngredientList(createRecipeDTO, updateEntity);
        log.info("Update recipe " + uuid + " is successful");
        return createRecipeDTO;
    }


    @Override
    @Transactional
    public void positiveVote(UUID uuid) {
        RecipeEntity updateEntity = recipeRepository.getById(uuid);
        updateEntity.setPositiveVotes(updateEntity.getPositiveVotes() + 1);
        recipeRepository.save(updateEntity);
    }

    @Override
    @Transactional
    public void negativeVote(UUID uuid) {
        RecipeEntity updateEntity = recipeRepository.getById(uuid);
        updateEntity.setNegativeVotes(updateEntity.getNegativeVotes() + 1);
        recipeRepository.save(updateEntity);
    }

    @Override
    @Transactional
    public void removeById(UUID uuid) {
        RecipeEntity removeEntity = recipeRepository.getById(uuid);
        recipeRepository.delete(removeEntity);
    }


    @Override
    @Transactional
    public List<RecipeDTO> getByCategoryName(String categoryName) {
        try {
            List<RecipeDTO> recipeDTOList = new ArrayList<>();
            RecipeDTO recipeDTO = new RecipeDTO();
            recipeDTOList.add(recipeDTO);

            CategoryEntity category = categoryRepository.getByName(categoryName);
            List<CategoryRecipeEntity> categoryRecipeEntityList =
                    categoryRecipeRepository.findAllByCategoryId(category.getId());
            List<RecipeEntity> recipeEntityList = new ArrayList<>();
            for (CategoryRecipeEntity categoryRecipeEntity : categoryRecipeEntityList) {
                RecipeEntity recipeEntity = recipeRepository.getById(categoryRecipeEntity.getRecipeId());
                recipeEntityList.add(recipeEntity);
            }
            map(recipeEntityList, recipeDTOList);
            return recipeDTOList;
        } catch (NotFoundException | NullPointerException ex) {
            log.error(new NotFoundException("NOT_FOUND"));
            return null;
        }
    }

    @Override
    @Transactional
    public List<RecipeDTO> getByCategory(UUID categoryId) {
        try {
            List<RecipeDTO> recipeDTOList = new ArrayList<>();
            RecipeDTO recipeDTO = new RecipeDTO();
            recipeDTOList.add(recipeDTO);

            List<CategoryRecipeEntity> categoryRecipeEntityList =
                    categoryRecipeRepository.findAllByCategoryId(categoryId);
            List<RecipeEntity> recipeEntityList = new ArrayList<>();
            for (CategoryRecipeEntity categoryRecipeEntity : categoryRecipeEntityList) {
                RecipeEntity recipeEntity = recipeRepository.getById(categoryRecipeEntity.getRecipeId());
                recipeEntityList.add(recipeEntity);
            }
            map(recipeEntityList, recipeDTOList);
            return recipeDTOList;
        } catch (NotFoundException | NullPointerException ex) {
            log.error(new NotFoundException("NOT_FOUND"));
            return null;
        }
    }

    @Override
    @Transactional
    public void saveCategoryRecipeEntityList(CreateRecipeDTO createRecipeDTO) {
        List<CategoryEntity> categoryEntityList = new ArrayList<>();
        CategoryEntity categoryEntityForMap = new CategoryEntity();
        categoryEntityList.add(categoryEntityForMap);
        map(createRecipeDTO.getCategoryRecipeDTOList(), categoryEntityList);

        List<CategoryRecipeEntity> categoryRecipeEntityList = new ArrayList<>();
        /*
        for (CategoryRecipeDTO categoryRecipeDTO : createRecipeDTO.getCategoryRecipeDTOList()) {
            CategoryRecipeEntity categoryRecipeEntity = new CategoryRecipeEntity();
            map(categoryRecipeDTO, categoryRecipeEntity);
            categoryRecipeRepository.save(categoryRecipeEntity);
        }
    */
        for (int i = 0; i < categoryEntityList.size(); i++) {

            CategoryRecipeEntity categoryRecipeEntity = new CategoryRecipeEntity();
            categoryRecipeEntityList.add(categoryRecipeEntity);
            CategoryEntity categoryEntity = categoryRepository.getByName(categoryEntityList.get(i).getName());
            categoryRecipeEntityList.get(i).setCategoryId(categoryEntity.getId());
            categoryRecipeEntityList.get(i).setRecipeId(createRecipeDTO.getId());
            map(categoryRecipeRepository.save(categoryRecipeEntityList.get(i)),
                    createRecipeDTO.getCategoryRecipeDTOList().get(i));
        }


    }

    @Override
    @Transactional
    public void saveCookingStepsEntityList(CreateRecipeDTO createRecipeDTO, RecipeEntity recipeEntity) {
        List<CookingStepsEntity> cookingStepsEntityList = new ArrayList<>();
        CookingStepsEntity cookingStepsEntityForMap = new CookingStepsEntity();
        cookingStepsEntityList.add(cookingStepsEntityForMap);
        map(createRecipeDTO.getCookingStepRecipeDTOList(), cookingStepsEntityList);
        for (int i = 0; i < cookingStepsEntityList.size(); i++) {
            cookingStepsEntityList.get(i).setActive(true);
            cookingStepsEntityList.get(i).setRecipeId(recipeEntity.getId());
            map(cookingStepsRepository.save(cookingStepsEntityList.get(i)),
                    createRecipeDTO.getCookingStepRecipeDTOList().get(i));
        }
    }

    @Override
    @Transactional
    public void saveRecipeIngredientList(CreateRecipeDTO createRecipeDTO, RecipeEntity recipeEntity) {
        List<RecipeIngredientEntity> recipeIngredientEntityList = new ArrayList<>();
        RecipeIngredientEntity recipeIngredientEntityForMap = new RecipeIngredientEntity();
        recipeIngredientEntityList.add(recipeIngredientEntityForMap);
        map(createRecipeDTO.getIngredientRecipeDTOList(), recipeIngredientEntityList);
        for (int i = 0; i < recipeIngredientEntityList.size(); i++) {
            IngredientEntity ingredientEntity =
                    ingredientRepository.getByName(createRecipeDTO.getIngredientRecipeDTOList().get(i).getName());
            recipeIngredientEntityList.get(i).setIngredientId(ingredientEntity.getId());
            recipeIngredientEntityList.get(i).setRecipeId(recipeEntity.getId());
            map(recipeIngredientRepository.save(recipeIngredientEntityList.get(i)),
                    createRecipeDTO.getIngredientRecipeDTOList().get(i));
        }
    }

    @Override
    @Transactional
    public CreateRecipeDTO createRecipeDTO(CreateRecipeDTO createRecipeDTO) {
        createRecipeDTO.setNegativeVotes(0);
        createRecipeDTO.setPositiveVotes(0);
        //save RecipeEntity
        RecipeEntity recipeEntity = new RecipeEntity();
        map(createRecipeDTO, recipeEntity);
        map(recipeRepository.save(recipeEntity), createRecipeDTO);
        //save CategoryRecipeEntityList
        saveCategoryRecipeEntityList(createRecipeDTO);
        //save CookingStepsEntityList
        saveCookingStepsEntityList(createRecipeDTO, recipeEntity);
        //save RecipeIngredientList
        saveRecipeIngredientList(createRecipeDTO, recipeEntity);
        log.info("Create recipe " + createRecipeDTO.getName() + " is successful");
        return createRecipeDTO;
    }

    @Override
    public CreateRecipeDTO mapCategoryRecipeEntityListToDTO(UUID uuid, CreateRecipeDTO createRecipeDTO) {
        List<CategoryRecipeDTO> categoryRecipeDTOList = new ArrayList<>();
        List<CategoryRecipeEntity> categoryRecipeEntityList = categoryRecipeRepository.findAllByRecipeId(uuid);
        for (CategoryRecipeEntity categoryRecipeEntity : categoryRecipeEntityList) {
            CategoryEntity categoryEntity = categoryRecipeEntity.getCategory();
            CategoryRecipeDTO categoryRecipeDTO = new CategoryRecipeDTO();
            map(categoryEntity, categoryRecipeDTO);
            categoryRecipeDTOList.add(categoryRecipeDTO);
            createRecipeDTO.setCategoryRecipeDTOList(categoryRecipeDTOList);
        }
        return createRecipeDTO;
    }

    @Override
    public CreateRecipeDTO mapCookingStepRecipeEntityListToDTO(RecipeEntity recipeEntity, CreateRecipeDTO createRecipeDTO) {
        CookingStepRecipeDTO cookingStepRecipeDTO = new CookingStepRecipeDTO();
        List<CookingStepRecipeDTO> cookingStepRecipeDTOList = new ArrayList<>();
        cookingStepRecipeDTOList.add(cookingStepRecipeDTO);
        createRecipeDTO.setCookingStepRecipeDTOList(cookingStepRecipeDTOList);
        map(recipeEntity.getCookingStepsEntityList(), createRecipeDTO.getCookingStepRecipeDTOList());
        return createRecipeDTO;
    }

    @Override
    public CreateRecipeDTO mapRecipeIngredientEntityListToDTO(RecipeEntity recipeEntity, CreateRecipeDTO createRecipeDTO) {
        IngredientRecipeDTO ingredientRecipeDTO = new IngredientRecipeDTO();
        List<IngredientRecipeDTO> ingredientRecipeDTOList = new ArrayList<>();
        ingredientRecipeDTOList.add(ingredientRecipeDTO);
        createRecipeDTO.setIngredientRecipeDTOList(ingredientRecipeDTOList);

        List<RecipeIngredientEntity> recipeIngredientEntityList = recipeEntity.getRecipeIngredientEntityList();
        List<IngredientEntity> ingredientEntityList = new ArrayList<>();
        for (RecipeIngredientEntity recipeIngredientEntity : recipeIngredientEntityList) {
            IngredientEntity ingredientEntity = recipeIngredientEntity.getIngredient();
            ingredientEntityList.add(ingredientEntity);
        }
        map(ingredientEntityList, createRecipeDTO.getIngredientRecipeDTOList());
        int size = createRecipeDTO.getIngredientRecipeDTOList().size();
        int countNull = size - 1;
        map(recipeEntity.getRecipeIngredientEntityList(), createRecipeDTO.getIngredientRecipeDTOList());
        for (int x = 0; x < countNull; x++) {
            createRecipeDTO.getIngredientRecipeDTOList().remove(size);
        }
        return createRecipeDTO;
    }

    @Override
    public CreateRecipeDTO getByRecipeId(UUID uuid) {
        //map recipeEntity to DTO
        RecipeEntity recipeEntity = recipeRepository.getById(uuid);
        CreateRecipeDTO createRecipeDTO = new CreateRecipeDTO();
        map(recipeEntity, createRecipeDTO);
        //map categoryRecipeEntityList to DTO
        map(mapCategoryRecipeEntityListToDTO(uuid, createRecipeDTO), createRecipeDTO);
        //map cookingStepRecipeEntityList to DTO
        map(mapCookingStepRecipeEntityListToDTO(recipeEntity, createRecipeDTO), createRecipeDTO);
        //map recipeIngredientEntityList to DTO
        map(mapRecipeIngredientEntityListToDTO(recipeEntity, createRecipeDTO), createRecipeDTO);
        return createRecipeDTO;
    }

    @Override
    public List<CreateRecipeDTO> getByAuthorId(UUID authorId) {
        List<CreateRecipeDTO> createRecipeDTOList = new ArrayList<>();
        List<RecipeEntity> recipeEntityList = recipeRepository.getByAuthorId(authorId);
        if (recipeEntityList.size() != 0) {
            for (RecipeEntity recipeEntity : recipeEntityList) {
                //map recipeEntity to DTO
                CreateRecipeDTO createRecipeDTO = new CreateRecipeDTO();
                map(recipeEntity, createRecipeDTO);
                createRecipeDTOList.add(createRecipeDTO);
                //map categoryRecipeEntityList to DTO
                map(mapCategoryRecipeEntityListToDTO(recipeEntity.getId(), createRecipeDTO), createRecipeDTO);
                //map cookingStepRecipeEntityList to DTO
                map(mapCookingStepRecipeEntityListToDTO(recipeEntity, createRecipeDTO), createRecipeDTO);
                //map recipeIngredientEntityList to DTO
                map(mapRecipeIngredientEntityListToDTO(recipeEntity, createRecipeDTO), createRecipeDTO);
            }
            return createRecipeDTOList;
        } else {
            return createRecipeDTOList;
        }
    }

    @Override
    @Transactional
    public List<RecipeDTO> getByAuthorName(String name) {
        List<RecipeDTO> recipeDTOList = new ArrayList<>();
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTOList.add(recipeDTO);

        UUID uuid = userRepository.getByLogin(name).getId();
        if (uuid == null) {
            log.error(new UserNotFoundException("User not found"));
            return null;
        }
        map(recipeRepository.getByAuthorId(uuid), recipeDTOList);
        return recipeDTOList;
    }

    @Override
    public List<RecipeDTO> findAllByName(String name) {
        List<RecipeDTO> recipeDTOList = new ArrayList<>();
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTOList.add(recipeDTO);
        map(recipeRepository.findAllByName(name), recipeDTOList);
        return recipeDTOList;
    }

    @Override
    public RecipeDTO getByNameAuthorId(String name, UUID uuid) {
        RecipeEntity recipeEntity = recipeRepository.getByNameAndAuthorId(name, uuid);
        RecipeDTO recipeDTO = new RecipeDTO();
        map(recipeEntity, recipeDTO);
        return recipeDTO;
    }

    @Override
    public List<RecipeDTO> findAllByIngredient(IngredientNameListDTO ingredientNameListDTO) {
        List<RecipeEntity> recipeEntityList = new ArrayList<>();
        List<IngredientEntity> ingredientEntityList = new ArrayList<>();
        for (int i = 0; i < ingredientNameListDTO.getIngredientNameDTOList().size(); i++) {
            IngredientEntity findIngredientEntity =
                    ingredientRepository.getByName(ingredientNameListDTO.getIngredientNameDTOList().get(i).getName());
            ingredientEntityList.add(findIngredientEntity);
            List<RecipeIngredientEntity> recipeIngredientEntityList =
                    recipeIngredientRepository.findAllByIngredientId(findIngredientEntity.getId());
            for (RecipeIngredientEntity recipeIngredientEntity : recipeIngredientEntityList) {
                RecipeEntity recipeEntity = recipeRepository.getById(recipeIngredientEntity.getRecipeId());
                recipeEntityList.add(recipeEntity);
            }
        }

        List<RecipeEntity> resultList = new ArrayList<>();
        Map<RecipeEntity, Integer> countMap = new HashMap<>();
        for (RecipeEntity item : recipeEntityList) {
            if (countMap.containsKey(item)) {
                countMap.put(item, countMap.get(item) + 1);
            } else countMap.put(item, 1);
            for (Object key : countMap.keySet().toArray()) {
                if (countMap.get(key) == 1) {
                    countMap.remove(key);
                }
            }
            if (!resultList.contains(item)) {
                resultList.add(item);
            }
        }

        List<RecipeDTO> recipeDTOList = new ArrayList<>();
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTOList.add(recipeDTO);

        map(resultList, recipeDTOList);
        return recipeDTOList;
    }

    @Override
    public List<RecipeDTO> findAllByData(RecipeDataDTO recipeDataDTO) {

        List<RecipeEntity> recipeEntityList = new ArrayList<>();
        int time = recipeDataDTO.getCookingTime();
        int zero = 0;
        if (time > 60) {
            zero = 61;
        }
        if (recipeDataDTO.getCookingDifficultyDTOList().size() == 0 && time != 0) {
            recipeEntityList.addAll(recipeRepository.findAllByCookingTimeBetween(zero, time));
        } else if (recipeDataDTO.getCookingDifficultyDTOList().size() != 0 && time != 0) {
            for (int i = 0; i < recipeDataDTO.getCookingDifficultyDTOList().size(); i++) {
                CookingDifficultyDTO difficultyDTO = recipeDataDTO.getCookingDifficultyDTOList().get(i);
                recipeEntityList.addAll(
                        recipeRepository.findAllByCookingTimeBetweenAndCookingDifficulty(
                                zero, time, CookingDifficulty.valueOf(difficultyDTO.getCookingDifficulty())));
            }

        } else if (recipeDataDTO.getCookingDifficultyDTOList().size() != 0 && time == 0) {
            for (int i = 0; i < recipeDataDTO.getCookingDifficultyDTOList().size(); i++) {
                CookingDifficultyDTO difficultyDTO = recipeDataDTO.getCookingDifficultyDTOList().get(i);
                recipeEntityList.addAll(recipeRepository.findAllByCookingDifficulty(
                        CookingDifficulty.valueOf(difficultyDTO.getCookingDifficulty())));
            }
        }

        List<RecipeDTO> recipeDTOList = new ArrayList<>();
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTOList.add(recipeDTO);
        map(recipeEntityList, recipeDTOList);
        return recipeDTOList;
    }

    @Override
    public RecipeDTO savePhoto(UUID id, MultipartFile file) throws IOException {
        RecipeEntity recipeEntity = recipeRepository.getById(id);
        String imageSrc = fileStorageService.storeFile(file);
        recipeEntity.setImgSource(imageSrc);
        RecipeDTO recipeDTO = new RecipeDTO();
        map(recipeRepository.save(recipeEntity), recipeDTO);
        return recipeDTO;
    }

    @Override
    public UserDTO getAuthorName(String authorId) {
        UserEntity userEntity = userRepository.getById(UUID.fromString(authorId));
        UserDTO userDTO = new UserDTO();
        map(userEntity, userDTO);
        return userDTO;
    }
}
