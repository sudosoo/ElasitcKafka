package com.sudosoo.takeItEasy.application.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig (
    @Value("\${devsoo.ip}")
    private val redisHost: String,
    @Value("\${spring.data.redis.port}")
    private val redisPort: Int
){

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        return LettuceConnectionFactory(redisHost, redisPort)
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, String> {
        var redisTemplate: RedisTemplate<String, String> = RedisTemplate()
        redisTemplate.keySerializer = StringRedisSerializer.UTF_8
        redisTemplate.valueSerializer = StringRedisSerializer.UTF_8
        redisTemplate.setDefaultSerializer(RedisSerializer.string())
        redisTemplate.setConnectionFactory(redisConnectionFactory())
        return redisTemplate
    }

    @Bean
    fun redisMessageListener(): RedisMessageListenerContainer {
        var container = RedisMessageListenerContainer()
        container.setConnectionFactory(redisConnectionFactory())
        return container
    }
}

