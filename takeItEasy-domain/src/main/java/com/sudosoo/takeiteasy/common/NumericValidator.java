package com.sudosoo.takeiteasy.common;

import com.sudosoo.takeiteasy.common.annotation.CustomDateFormatValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NumericValidator implements ConstraintValidator<CustomDateFormatValidator, String> {

    @Override
    public void initialize(CustomDateFormatValidator constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && value.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}") && value.length() == 16;
    }
}
