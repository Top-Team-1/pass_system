package ru.top.pass_system.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static ru.top.pass_system.consts.RegexConstants.PHONE_REGEX;

public class PhoneValidator implements ConstraintValidator<ValidPhone, String> {

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext constraintValidatorContext) {
        if (phone == null) {
            return false;
        }
        return phone.matches(PHONE_REGEX);
    }
}