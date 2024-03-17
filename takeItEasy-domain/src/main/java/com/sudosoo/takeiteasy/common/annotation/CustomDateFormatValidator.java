package com.sudosoo.takeiteasy.common.annotation;

import com.sudosoo.takeiteasy.common.NumericValidator;
import jakarta.validation.Constraint;
import org.springframework.messaging.handler.annotation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Constraint(validatedBy = NumericValidator.class)
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomDateFormatValidator {
    String message() default "올바른 날짜 및 시간 형식이 아닙니다. (yyyy-MM-dd HH:mm)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
