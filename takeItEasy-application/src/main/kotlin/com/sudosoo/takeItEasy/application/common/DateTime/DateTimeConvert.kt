package com.sudosoo.takeItEasy.application.common.DateTime

import com.sudosoo.takeItEasy.application.common.DateTime.DateTimePattern
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

object DateTimeConvert {
    fun convertToString(dateTime: LocalDateTime, pattern : DateTimePattern = DateTimePattern.STANDARD): String {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(pattern.pattern)
        return dateTime.format(formatter)
    }

    fun convertToDateTime(dateTimeStr: String, pattern: DateTimePattern = DateTimePattern.STANDARD) : LocalDateTime {
        try {
            val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(pattern.pattern)
            return LocalDateTime.parse(dateTimeStr, formatter)
        } catch (e: DateTimeParseException) {
            throw Exception("DateTimeParseException : Invalid format")
        }
    }
}