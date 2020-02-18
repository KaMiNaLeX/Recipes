package com.samsolutions.recipes.service.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

/**
 * @author Kaminskiy Alexey
 * @since 2020.02
 */
public class UuidValidator implements ConstraintValidator<ValidUUID, UUID> {
    private final String regex = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$"; // the regex

    @Override
    public void initialize(ValidUUID constraintAnnotation) {
    }

    @Override
    public boolean isValid(UUID uuid, ConstraintValidatorContext constraintValidatorContext) {
        return uuid.toString().matches(this.regex);
    }
}
