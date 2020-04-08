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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;


/**
 * @author kaminskiy.alexey
 * @since 2019.12
 */
@Log4j2
@Service
public class RecipeServiceImpl extends ModelMapperService implements RecipeService {
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

    private static int getCountAllRecipesByIngredient;
    private static int getCountAllRecipesByData;

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
        saveCookingStepsEntityList(createRecipeDTO);
        //save RecipeIngredientList
        saveRecipeIngredientList(createRecipeDTO);
        List<UUID> updateCookingStepsEntityListUUID = updateCookingStepsEntityList(createRecipeDTO);
        for (int i = 0; i < updateCookingStepsEntityListUUID.size(); i++) {
            createRecipeDTO.getCookingStepRecipeDTOList().get(i).setId(updateCookingStepsEntityListUUID.get(i));
        }
        log.info("Create recipe " + createRecipeDTO.getName() + " is successful");
        return createRecipeDTO;
    }

    @Override
    @Transactional
    public CreateRecipeDTO updateRecipe(UUID uuid, CreateRecipeDTO createRecipeDTO) {
        RecipeEntity updateEntity = recipeRepository.getById(uuid);
        createRecipeDTO.setId(updateEntity.getId());
        map(createRecipeDTO, updateEntity);
        map(recipeRepository.save(updateEntity), createRecipeDTO);
        //update category
        updateCategory(createRecipeDTO);
        //update RecipeIngredientList
        updateRecipeIngredientList(createRecipeDTO);
        //update CookingStepsEntityList
        List<UUID> updateCookingStepsEntityListUUID = updateCookingStepsEntityList(createRecipeDTO);
        for (int i = 0; i < updateCookingStepsEntityListUUID.size(); i++) {
            createRecipeDTO.getCookingStepRecipeDTOList().get(i).setId(updateCookingStepsEntityListUUID.get(i));
        }
        log.info("Update recipe " + uuid + " is successful");
        return createRecipeDTO;
    }

    @Override
    @Transactional
    public void updateCategory(CreateRecipeDTO createRecipeDTO) {
        for (CategoryRecipeEntity categoryRecipeEntity : categoryRecipeRepository.findAllByRecipeId(createRecipeDTO.getId())) {
            categoryRecipeRepository.delete(categoryRecipeEntity);
        }
        saveCategoryRecipeEntityList(createRecipeDTO);
    }

    @Override
    @Transactional
    public List<UUID> updateCookingStepsEntityList(CreateRecipeDTO createRecipeDTO) {
        for (CookingStepsEntity value : cookingStepsRepository.findAllByRecipeId(createRecipeDTO.getId())) {
            cookingStepsRepository.delete(value);
        }
        return saveCookingStepsEntityList(createRecipeDTO);
    }

    @Override
    @Transactional
    public void updateRecipeIngredientList(CreateRecipeDTO createRecipeDTO) {
        for (RecipeIngredientEntity entity : recipeIngredientRepository.findAllByRecipeId(createRecipeDTO.getId())) {
            recipeIngredientRepository.delete(entity);
        }
        saveRecipeIngredientList(createRecipeDTO);
    }

    @Override
    @Transactional
    public void saveCategoryRecipeEntityList(CreateRecipeDTO createRecipeDTO) {
        for (int i = 0; i < createRecipeDTO.getCategoryRecipeDTOList().size(); i++) {
            CategoryRecipeEntity categoryRecipeEntity = new CategoryRecipeEntity();
            CategoryEntity categoryEntity = categoryRepository.getByName(createRecipeDTO.getCategoryRecipeDTOList().get(i).getName());
            categoryRecipeEntity.setCategoryId(categoryEntity.getId());
            categoryRecipeEntity.setRecipeId(createRecipeDTO.getId());
            categoryRecipeRepository.save(categoryRecipeEntity);
        }
    }

    @Override
    @Transactional
    public List<UUID> saveCookingStepsEntityList(CreateRecipeDTO createRecipeDTO) {
        List<UUID> cookingStepsEntitiesUUID = new ArrayList<>();
        for (CookingStepsEntity stepsEntity : mapListLambda(createRecipeDTO.getCookingStepRecipeDTOList(), CookingStepsEntity.class)) {
            stepsEntity.setRecipeId(createRecipeDTO.getId());
            stepsEntity.setRecipe(recipeRepository.getById(createRecipeDTO.getId()));
            cookingStepsRepository.save(stepsEntity);
            cookingStepsEntitiesUUID.add(stepsEntity.getId());
        }
        return cookingStepsEntitiesUUID;
    }

    @Override
    @Transactional
    public void saveRecipeIngredientList(CreateRecipeDTO createRecipeDTO) {
        for (int i = 0; i < createRecipeDTO.getIngredientRecipeDTOList().size(); i++) {
            IngredientEntity ingredientEntity =
                    ingredientRepository.getByName(createRecipeDTO.getIngredientRecipeDTOList().get(i).getName());
            RecipeIngredientEntity recipeIngredientEntity = new RecipeIngredientEntity();
            recipeIngredientEntity.setIngredientId(ingredientEntity.getId());
            recipeIngredientEntity.setRecipeId(createRecipeDTO.getId());
            recipeIngredientEntity.setAmount(createRecipeDTO.getIngredientRecipeDTOList().get(i).getAmount());
            recipeIngredientEntity.setUnit(createRecipeDTO.getIngredientRecipeDTOList().get(i).getUnit());
            recipeIngredientEntity.setUnitRu(createRecipeDTO.getIngredientRecipeDTOList().get(i).getUnitRu());
            recipeIngredientEntity.setNote(createRecipeDTO.getIngredientRecipeDTOList().get(i).getNote());
            recipeIngredientEntity.setNoteRu(createRecipeDTO.getIngredientRecipeDTOList().get(i).getNoteRu());
            recipeIngredientRepository.save(recipeIngredientEntity);
        }
    }

    @Override
    @Transactional
    public List<RecipeDTO> getByCategoryName(String categoryName, int page, int size, String sort) {
        try {
            CategoryEntity category;
            if (categoryName.matches("^[A-Za-z\\s]+$")) {
                category = categoryRepository.getByName(categoryName);
            } else category = categoryRepository.getByNameRu(categoryName);

            Pageable pageable = PageRequest.of(page, size);
            List<RecipeEntity> recipeEntityList = new ArrayList<>();
            for (CategoryRecipeEntity categoryRecipeEntity :
                    categoryRecipeRepository.findAllByCategoryId(category.getId(), pageable).getContent()) {
                RecipeEntity recipeEntity = recipeRepository.getById(categoryRecipeEntity.getRecipeId());
                recipeEntityList.add(recipeEntity);
            }
            recipeEntityList.sort(new Comparator<RecipeEntity>() {
                @Override
                public int compare(RecipeEntity o1, RecipeEntity o2) {
                    if ("cookingDifficulty".equals(sort)) {
                        return o1.getCookingDifficulty().compareTo(o2.getCookingDifficulty());
                    }
                    return o1.getName().compareTo(o2.getName());
                }
            });
            return mapListLambda(recipeEntityList, RecipeDTO.class);
        } catch (NotFoundException | NullPointerException ex) {
            log.error(new NotFoundException("NOT_FOUND"));
            return null;
        }
    }

    @Override
    public int getCountAllRecipesInCategory(String categoryName) {
        if (categoryName.matches("^[A-Za-z\\s]+$")) {
            return categoryRecipeRepository.findAllByCategoryName(categoryName).size();
        } else return categoryRecipeRepository.findAllByCategoryNameRu(categoryName).size();
    }

    @Override
    public int getCountAllOwnRecipes(UUID authorId) {
        return recipeRepository.findAllByAuthorId(authorId).size();
    }

    @Override
    public CreateRecipeDTO mapCategoryRecipeEntityListToDTO(UUID uuid, CreateRecipeDTO createRecipeDTO) {
        List<CategoryRecipeDTO> categoryRecipeDTOList = new ArrayList<>();
        for (CategoryRecipeEntity categoryRecipeEntity : categoryRecipeRepository.findAllByRecipeId(uuid)) {
            CategoryRecipeDTO categoryRecipeDTO = new CategoryRecipeDTO();
            categoryRecipeDTO.setName(categoryRecipeEntity.getCategory().getName());
            categoryRecipeDTO.setNameRu(categoryRecipeEntity.getCategory().getNameRu());
            categoryRecipeDTOList.add(categoryRecipeDTO);
            createRecipeDTO.setCategoryRecipeDTOList(categoryRecipeDTOList);
        }
        return createRecipeDTO;
    }

    @Override
    public CreateRecipeDTO mapCookingStepRecipeEntityListToDTO(RecipeEntity recipeEntity, CreateRecipeDTO createRecipeDTO) {
        createRecipeDTO.setCookingStepRecipeDTOList(mapListLambda(recipeEntity.getCookingStepsEntityList(), CookingStepRecipeDTO.class));
        return createRecipeDTO;
    }

    @Override
    public CreateRecipeDTO mapRecipeIngredientEntityListToDTO(RecipeEntity recipeEntity, CreateRecipeDTO createRecipeDTO) {
        createRecipeDTO.setIngredientRecipeDTOList(mapListLambda(recipeEntity.getRecipeIngredientEntityList(), IngredientRecipeDTO.class));

        List<RecipeIngredientEntity> recipeIngredientEntityList = recipeEntity.getRecipeIngredientEntityList();
        List<IngredientEntity> ingredientEntityList = new ArrayList<>();
        for (RecipeIngredientEntity recipeIngredientEntity : recipeIngredientEntityList) {
            IngredientEntity ingredientEntity = recipeIngredientEntity.getIngredient();
            ingredientEntityList.add(ingredientEntity);
        }
        for (int i = 0; i < ingredientEntityList.size(); i++) {
            createRecipeDTO.getIngredientRecipeDTOList().get(i).setName(ingredientEntityList.get(i).getName());
            createRecipeDTO.getIngredientRecipeDTOList().get(i).setNameRu(ingredientEntityList.get(i).getNameRu());
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
    public List<CreateRecipeDTO> getByAuthorId(UUID authorId, int page, int size, String sort) {
        List<CreateRecipeDTO> createRecipeDTOList = new ArrayList<>();
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        List<RecipeEntity> recipeEntityList = recipeRepository.getByAuthorId(authorId, pageable).getContent();
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
        }
        return createRecipeDTOList;
    }

    @Override
    @Transactional
    public List<RecipeDTO> getByAuthorName(String name, int page, int size, String sort) {
        UUID uuid = userRepository.getByLogin(name).getId();
        if (uuid == null) {
            log.error(new UserNotFoundException("User not found"));
            return null;
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return mapListLambda(recipeRepository.getByAuthorId(uuid, pageable).getContent(), RecipeDTO.class);
    }

    @Override
    public int getCountAllRecipesByAuthorName(String name) {
        UUID uuid = userRepository.getByLogin(name).getId();
        if (uuid == null) {
            log.error(new UserNotFoundException("User not found"));
            return 0;
        }
        return recipeRepository.getByAuthorId(uuid).size();
    }

    @Override
    public List<RecipeDTO> findAllByName(String name, int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        if (name.matches("^[A-Za-z\\s]+$")) {
            return mapListLambda(recipeRepository.findAllByName(name, pageable).getContent(), RecipeDTO.class);
        } else return mapListLambda(recipeRepository.findAllByNameRu(name, pageable).getContent(), RecipeDTO.class);

    }

    @Override
    public int getCountAllRecipesByName(String name) {
        if (name.matches("^[A-Za-z\\s]+$")) {
            return recipeRepository.findAllByName(name).size();
        } else return recipeRepository.findAllByNameRu(name).size();
    }

    @Override
    public RecipeDTO getByNameAuthorId(String name, UUID uuid) {
        RecipeEntity recipeEntity = recipeRepository.getByNameAndAuthorId(name, uuid);
        if (recipeEntity == null) {
            return null;
        } else {
            RecipeDTO recipeDTO = new RecipeDTO();
            map(recipeEntity, recipeDTO);
            return recipeDTO;
        }
    }

    @Override
    public List<RecipeDTO> findAllByIngredient(IngredientNameListDTO ingredientNameListDTO, int page, int size, String sort) {
        List<RecipeEntity> recipeEntityList = new ArrayList<>();
        Pageable pageable = PageRequest.of(page, size);
        for (int i = 0; i < ingredientNameListDTO.getIngredientNameDTOList().size(); i++) {
            IngredientEntity findIngredientEntity;
            if (ingredientNameListDTO.getIngredientNameDTOList().get(i).getName().matches("^[A-Za-z\\s]+$")) {
                findIngredientEntity = ingredientRepository.getByName(ingredientNameListDTO.getIngredientNameDTOList().get(i).getName());
            } else
                findIngredientEntity = ingredientRepository.getByNameRu(ingredientNameListDTO.getIngredientNameDTOList().get(i).getName());

            List<RecipeIngredientEntity> recipeIngredientEntityList =
                    recipeIngredientRepository.findAllByIngredientId(findIngredientEntity.getId(), pageable).getContent();
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
        resultList.sort(new Comparator<RecipeEntity>() {
            @Override
            public int compare(RecipeEntity o1, RecipeEntity o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        getCountAllRecipesByIngredient = resultList.size();
        if (resultList.size() > size) {
            int deleted = (resultList.size() - size);
            for (int i = 0; i < deleted; i++) {
                resultList.remove((size - 1) + 1);
            }
        }
        return mapListLambda(resultList, RecipeDTO.class);
    }

    @Override
    public int getCountAllRecipesByIngredient() {
        return getCountAllRecipesByIngredient;
    }

    @Override
    public List<RecipeDTO> findAllByData(RecipeDataDTO recipeDataDTO, int page, int size, String sort) {
        boolean ru = false;
        if (recipeDataDTO.getCookingDifficultyDTOList().size() != 0) {
            if (recipeDataDTO.getCookingDifficultyDTOList().get(0).getCookingDifficulty().matches("^[А-Яа-я\\s]+$")) {
                ru = true;
            }
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        List<RecipeEntity> recipeEntityList = new ArrayList<>();
        int time = recipeDataDTO.getCookingTime();
        int zero = 0;
        if (time > 60) {
            zero = 61;
        }
        if (recipeDataDTO.getCookingDifficultyDTOList().size() == 0 && time != 0) {
            recipeEntityList.addAll(recipeRepository.findAllByCookingTimeBetween(zero, time, pageable).getContent());
        } else if (recipeDataDTO.getCookingDifficultyDTOList().size() != 0 && time != 0) {
            for (int i = 0; i < recipeDataDTO.getCookingDifficultyDTOList().size(); i++) {
                CookingDifficultyDTO difficultyDTO = recipeDataDTO.getCookingDifficultyDTOList().get(i);
                if (ru) {
                    recipeEntityList.addAll(
                            recipeRepository.findAllByCookingTimeBetweenAndCookingDifficultyRu(
                                    zero, time, CookingDifficulty.valueOf(difficultyDTO.getCookingDifficulty()), pageable).getContent());
                } else {
                    recipeEntityList.addAll(
                            recipeRepository.findAllByCookingTimeBetweenAndCookingDifficulty(
                                    zero, time, CookingDifficulty.valueOf(difficultyDTO.getCookingDifficulty()), pageable).getContent());
                }

            }

        } else if (recipeDataDTO.getCookingDifficultyDTOList().size() != 0 && time == 0) {
            for (int i = 0; i < recipeDataDTO.getCookingDifficultyDTOList().size(); i++) {
                CookingDifficultyDTO difficultyDTO = recipeDataDTO.getCookingDifficultyDTOList().get(i);
                if (ru) {
                    recipeEntityList.addAll(recipeRepository.findAllByCookingDifficultyRu(
                            CookingDifficulty.valueOf(difficultyDTO.getCookingDifficulty()), pageable).getContent());
                } else {
                    recipeEntityList.addAll(recipeRepository.findAllByCookingDifficulty(
                            CookingDifficulty.valueOf(difficultyDTO.getCookingDifficulty()), pageable).getContent());
                }
            }
        }
        getCountAllRecipesByData = recipeEntityList.size();
        if (recipeEntityList.size() > size) {
            int deleted = (recipeEntityList.size() - size);
            for (int i = 0; i < deleted; i++) {
                recipeEntityList.remove((size - 1) + 1);
            }
        }
        return mapListLambda(recipeEntityList, RecipeDTO.class);
    }

    @Override
    public int getCountAllRecipesByData() {
        return getCountAllRecipesByData;
    }

    @Override
    public RecipeDTO savePhoto(UUID id, MultipartFile file) throws IOException {
        RecipeEntity recipeEntity = recipeRepository.getById(id);
        String imageSrc;
        if (Objects.equals(file.getOriginalFilename(), "null")) imageSrc = "http://localhost:4200/getFile/noImage.png";
        else {
            imageSrc = fileStorageService.storeFile(file);
        }
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

    @Override
    public List<RecipeDTO> findAll() {
        return mapListLambda(recipeRepository.findAll(), RecipeDTO.class);
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
}
