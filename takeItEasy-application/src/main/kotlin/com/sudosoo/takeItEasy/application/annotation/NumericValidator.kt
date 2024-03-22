package com.sudosoo.takeItEasy.application.annotation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class NumericValidator : ConstraintValidator<CustomDateFormatValidator?, String?> {
    override fun initialize(constraintAnnotation: CustomDateFormatValidator?) {
    }

    override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
        return value != null && value.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}".toRegex()) && value.length == 16
    }
}
