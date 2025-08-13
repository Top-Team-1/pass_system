package ru.top.pass_system.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static ru.top.pass_system.consts.RegexConstants.NAME_REGEX;

public class NameValidator implements ConstraintValidator<ValidName, String> {

    @Override
    public void initialize(ValidName validName) {
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        return name != null && name.matches(NAME_REGEX);
    }
}
