package com.samsolutions.recipes.controller;

import com.github.javafaker.Faker;
import com.samsolutions.recipes.model.Enum.Type;
import com.samsolutions.recipes.model.IngredientEntity;
import com.samsolutions.recipes.model.UserEntity;
import com.samsolutions.recipes.repository.IngredientRepository;
import com.samsolutions.recipes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * This controller is used to generate random fake data. To the programmer not to invent values, we use a faker.
 * This controller is not used in production.
 *
 * @author kaminskiy.alexey
 * @since 2019.10
 */
@RestController
@RequestMapping("/random")
public class FakeDataGeneratorController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IngredientRepository ingredientRepository;

    private Faker faker = new Faker();

    //Returns a random double
    public double randomDouble(int maxNumberOfDecimals, long min, long max) {
        return decimalBetween(min, max)
                .setScale(maxNumberOfDecimals, BigDecimal.ROUND_HALF_DOWN)
                .doubleValue();
    }

    private BigDecimal decimalBetween(long min, long max) {
        if (min == max) {
            return new BigDecimal(min);
        }
        final long trueMin = Math.min(min, max);
        final long trueMax = Math.max(min, max);

        final double range = (double) trueMax - (double) trueMin;

        final double chunkCount = Math.sqrt(Math.abs(range));
        final double chunkSize = chunkCount;
        final long randomChunk = faker.random().nextLong((long) chunkCount);

        final double chunkStart = trueMin + randomChunk * chunkSize;
        final double adj = chunkSize * faker.random().nextDouble();
        return new BigDecimal(chunkStart + adj);
    }

    @GetMapping("/users")
    public List<UserEntity> getRandomUser() {
        List<UserEntity> userEntityList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            UserEntity userEntity = new UserEntity();
            userEntity.setFirstName(faker.name().firstName());
            userEntity.setLastName(faker.name().lastName());
            userEntity.setEmail(faker.bothify("????##@gmail.com"));
            userEntity.setLogin(faker.witcher().monster());
            userEntity.setPassword(passwordEncoder.encode(faker.bothify("??#???#??")));
            userEntityList.add(userEntity);
            userRepository.save(userEntity);
        }
        return userEntityList;
    }

    @GetMapping("/ingredients")
    public List<IngredientEntity> getRandomIngredient() {
        List<IngredientEntity> ingredientEntityList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            IngredientEntity ingredientEntity = new IngredientEntity();
            ingredientEntity.setCalories(randomDouble(2, 10, 1000));
            ingredientEntity.setDescription("");
            ingredientEntity.setName(faker.food().ingredient());
            ingredientEntity.setType(Type.VEGETABLE);
            ingredientEntityList.add(ingredientEntity);
            ingredientRepository.save(ingredientEntity);
        }
        return ingredientEntityList;
    }


}
