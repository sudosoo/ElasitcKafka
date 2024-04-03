package com.sudosoo.takeItEasy.application.redis

import org.springframework.data.domain.PageRequest

interface RedisService {
    fun saveReadValue(value: Any)
    fun <T> findByPagination(responseDtoName: String, pageable: PageRequest): MutableList<T>
    fun <T> getValues(methodName: String): MutableList<T>
    fun postRepositoryRedisSynchronization()
}
