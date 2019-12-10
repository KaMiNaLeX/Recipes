package com.samsolutions.recipes;

import com.samsolutions.recipes.config.JwtTokenProvider;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
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

}
