package com.sudosoo.takeItEasy.application.common.specification

open class SpecificationDto(
    val columnName : String,
    val paramName : String = columnName,
) {
    open fun isEmpty(value : Any) : Boolean{
        if (value is String) return value.isEmpty()
        return value.equals(null)
    }
}