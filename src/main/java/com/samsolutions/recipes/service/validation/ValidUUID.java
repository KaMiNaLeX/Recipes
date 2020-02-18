package com.samsolutions.recipes.service.validation;

import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Kaminskiy Alexey
 * @since 2020.02
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RUNTIME)
public @interface ValidUUID {
    String message() default "{invalid.uuid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
