package com.samsolutions.recipes;

import com.samsolutions.recipes.config.JwtTokenProvider;
import com.samsolutions.recipes.controller.CookingStepsRestController;
import com.samsolutions.recipes.controller.FavoritesRestController;
import com.samsolutions.recipes.controller.GetFileController;
import com.samsolutions.recipes.controller.RecipeRestController;
import com.samsolutions.recipes.service.FavoriteService;
import com.samsolutions.recipes.service.FileStorageService;
import com.samsolutions.recipes.service.RecipeService;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author kaminskiy.alexey
 * @since 2019.11
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestPropertySource(properties = "spring.flyway.enabled=false")
public abstract class BaseTest {

    @MockBean
    JwtTokenProvider jwtTokenProvider;

    @MockBean
    private CookingStepsRestController cookingStepsRestController;

    @MockBean
    private FavoritesRestController favoritesRestController;

    @MockBean
    private RecipeRestController recipeRestController;

    @MockBean
    private GetFileController getFileController;

    @MockBean
    private FileStorageService fileStorageService;

    @MockBean
    private FavoriteService favoriteService;

    @MockBean
    private RecipeService recipeService;

}
