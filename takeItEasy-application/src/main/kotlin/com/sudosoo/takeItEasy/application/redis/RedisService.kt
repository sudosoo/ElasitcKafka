package com.sudosoo.takeItEasy.application.redis

import com.sudosoo.takeItEasy.domain.entity.Post
import org.springframework.data.domain.PageRequest

interface RedisService {
    fun saveReadValue(value: Any)
    fun <T> findByPaginationPost(responseDtoName: String, pageable: PageRequest): MutableList<T>
    fun <T> getValues(methodName: String): MutableList<T>
    fun postRepositoryRedisSynchronization(posts : MutableList<Post>)
}
