package com.sudosoo.takeItEasy.application.common.DateTime

enum class DateTimePattern(val pattern : String) {
    STANDARD("yyyy-MM-dd HH:mm:ss"),
    COMPACT("yyyyMMddHHmmss"),
}