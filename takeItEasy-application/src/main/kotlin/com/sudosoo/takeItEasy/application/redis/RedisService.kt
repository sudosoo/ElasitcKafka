package com.sudosoo.takeItEasy.application.redis

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.sudosoo.takeItEasy.application.dto.post.PostCQRSDto
import com.sudosoo.takeItEasy.domain.entity.Post
import org.springframework.data.domain.PageRequest
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.IOException

@Service
@Transactional
class RedisService(
    val redisTemplate: RedisTemplate<String, String>,
) {

    private companion object {
        val objectMapper = ObjectMapper()
    }

    fun <T> findByPagination(responseDtoName: String, pageable: PageRequest): MutableList<T & Any> {
        val clazz = try {
            Class.forName(responseDtoName)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
            return mutableListOf()
        }

        val start = pageable.pageNumber * pageable.pageSize
        val end = start + pageable.pageSize - 1

        val jsonValues = redisTemplate.opsForList().range(responseDtoName, start.toLong(), end.toLong())
        checkNotNull(jsonValues)
            return jsonValues.mapNotNull { jsonValue ->
                try {
                    objectMapper.readValue(jsonValue, clazz)?.let {
                        clazz.cast(it) as T
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    null // Skip invalid entries
                }
            }.toMutableList()
    }

    fun <T> getValues(methodName: String): MutableList<T> {
        return try {
            val fullClassName = "com.sudosoo.takeItEasy.application.dto.$methodName"
            val clazz = Class.forName(fullClassName) as Class<T>
            val jsonValues = redisTemplate.opsForList().range(methodName, 0, -1)

            checkNotNull(jsonValues)
            jsonValues.mapNotNull { jsonValue ->
                try {
                    objectMapper.readValue(jsonValue, clazz)
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
            }.toMutableList()
        } catch (e: Exception) {
            e.printStackTrace()
            mutableListOf() // Return empty list on errors
        }
    }

    fun saveReadValue(value: Any) {
        val className = value.javaClass.simpleName
        val jsonObject = try {
            objectMapper.writeValueAsString(value)
        } catch (e: JsonProcessingException) {
            throw IllegalArgumentException("Invalid value")
        }
        redisTemplate.opsForList().leftPush(className, jsonObject)
    }

    fun resetRedisCacheWithAllPosts(topicName: String, posts: List<Post>) {
        redisTemplate.delete(topicName)
        posts.map { post ->
            var jsonPost : String
                try {
                    val postTitle = PostCQRSDto(post.title, post.writer)
                    jsonPost = objectMapper.writeValueAsString(postTitle)
                } catch (e: JsonProcessingException) {
                    throw IllegalArgumentException("Invalid value")
                }
            redisTemplate.opsForList().leftPush("PostResponseDto", jsonPost)
        }
    }



}