package com.sudosoo.takeItEasy.application.commons

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

object CommonService {

    fun getObjectMapper(): ObjectMapper {
        return ObjectMapper()
    }
    fun <E> checkNotNullData(e: E?, message: String?): Boolean {
        if (e == null) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, message)
        }
        return true
    }
}
