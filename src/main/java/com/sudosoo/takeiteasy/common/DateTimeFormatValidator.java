package com.sudosoo.takeiteasy.common;

import com.sudosoo.takeiteasy.common.annotation.CustomDateTimeFormat;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateTimeFormatValidator implements ConstraintValidator<CustomDateTimeFormat, String> {
    private String pattern;

    @Override
    public void initialize(CustomDateTimeFormat constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return value.equals(pattern);
    }
}
