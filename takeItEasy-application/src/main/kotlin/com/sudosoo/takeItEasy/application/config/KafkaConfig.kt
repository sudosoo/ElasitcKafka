package com.sudosoo.takeItEasy.application.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.*
import org.springframework.kafka.listener.ContainerProperties
import org.springframework.kafka.listener.KafkaMessageListenerContainer
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer
import java.time.Duration


@Configuration
class KafkaConfig (
    @Value("\${spring.kafka.bootstrap-servers}")
    val bootstrapAddress: String,
    @Value("\${devsoo.kafka.restapi.reply.topic}")
    var kafkaRestApiReplyTopic: String )
{
    @Bean
    fun producerFactory(): ProducerFactory<String, String> {
        val props = mutableMapOf<String, Any>()
        props[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        props[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        return DefaultKafkaProducerFactory(props)
    }

    @Bean
    fun consumerFactory(): ConsumerFactory<String, String> {
        val props = mutableMapOf<String, Any>()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = ErrorHandlingDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = ErrorHandlingDeserializer::class.java
        props[ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS] = StringDeserializer::class.java
        props[ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS] = StringDeserializer::class.java
        props[ConsumerConfig.GROUP_ID_CONFIG] = "post-server-consumer-group"
        props[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = "latest"
        props[ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG] = true
        return DefaultKafkaConsumerFactory(props)
    }

    @Bean
    fun replyKafkaTemplate(): ReplyingKafkaTemplate<String, String, String> {
        val replyingKafkaTemplate = ReplyingKafkaTemplate(producerFactory(), replyContainer())
        replyingKafkaTemplate.setDefaultReplyTimeout(Duration.ofMillis(5000))
        return replyingKafkaTemplate
    }

    @Bean
    fun replyContainer(): KafkaMessageListenerContainer<String, String> {
        val containerProperties = ContainerProperties(kafkaRestApiReplyTopic)
        return KafkaMessageListenerContainer(consumerFactory(), containerProperties)
    }

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, String>()
        factory.consumerFactory = consumerFactory()
        factory.isBatchListener = false
        factory.containerProperties.pollTimeout = 1000
        factory.containerProperties.idleEventInterval = 10000L
        factory.setConcurrency(3)
        factory.setReplyTemplate(kafkaTemplate())
        return factory
    }

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, String> {
        return KafkaTemplate(producerFactory())
    }


}
