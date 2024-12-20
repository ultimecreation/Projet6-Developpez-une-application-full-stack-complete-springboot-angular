package com.openclassrooms.MddApi.validation;

import org.springframework.beans.factory.annotation.Autowired;

import com.openclassrooms.MddApi.entity.User;
import com.openclassrooms.MddApi.repository.UserRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmailConstraint, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        User emailExists = userRepository.findByEmail(value);
        return emailExists == null ? true : false;
    }

}
