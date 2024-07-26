package com.sudosoo.takeItEasy.application.service.event

import com.fasterxml.jackson.databind.ObjectMapper
import com.sudosoo.takeItEasy.application.commons.jpa.JpaService
import com.sudosoo.takeItEasy.application.commons.specification.JpaSpecificService
import com.sudosoo.takeItEasy.application.kafka.KafkaProducer
import com.sudosoo.takeItEasy.domain.entity.Event
import com.sudosoo.takeItEasy.domain.entity.EventOperation
import com.sudosoo.takeItEasy.domain.entity.KafkaTopics
import com.sudosoo.takeItEasy.domain.repository.DeadLetterRepository
import com.sudosoo.takeItEasy.domain.repository.common.BaseRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class EventManager(
    private val repository: DeadLetterRepository,
    private val kafkaProducer: KafkaProducer,
    private val objectMapper: ObjectMapper,

    ) :JpaService<Event, Long>, JpaSpecificService<Event, Long> {
    override var jpaRepository: BaseRepository<Event, Long> = repository
    override val jpaSpecRepository: BaseRepository<Event, Long> = repository

    fun create(topics: KafkaTopics, operation: EventOperation, body: Any): Event {
        val jsonBody = objectMapper.writeValueAsString(body)
        return Event(topics, operation , jsonBody)
    }
}