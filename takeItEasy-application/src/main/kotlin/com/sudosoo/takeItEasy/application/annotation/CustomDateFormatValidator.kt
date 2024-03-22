package com.sudosoo.takeItEasy.application.annotation

import jakarta.validation.Constraint
import org.springframework.messaging.handler.annotation.Payload
import kotlin.reflect.KClass
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [NumericValidator::class])
annotation class CustomDateFormatValidator(
    val message: String = "올바른 날짜 및 시간 형식이 아닙니다. (yyyy-MM-dd HH:mm)",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)