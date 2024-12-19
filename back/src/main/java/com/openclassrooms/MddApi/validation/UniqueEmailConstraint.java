package com.openclassrooms.MddApi.validation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = UniqueEmailValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmailConstraint {

    String message() default "Email already in use";

    String email() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}