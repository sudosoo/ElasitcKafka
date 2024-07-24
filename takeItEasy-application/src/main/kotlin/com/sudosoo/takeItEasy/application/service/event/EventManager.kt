package com.sudosoo.takeItEasy.application.service.event

import com.fasterxml.jackson.databind.ObjectMapper
import com.sudosoo.takeItEasy.application.common.jpa.JpaService
import com.sudosoo.takeItEasy.application.common.specification.JpaSpecificService
import com.sudosoo.takeItEasy.application.kafka.KafkaProducer
import com.sudosoo.takeItEasy.domain.entity.Event
import com.sudosoo.takeItEasy.domain.entity.EventOperation
import com.sudosoo.takeItEasy.domain.entity.KafkaTopics
import com.sudosoo.takeItEasy.domain.repository.DeadLetterRepository
import com.sudosoo.takeItEasy.domain.repository.common.BaseRepository
import org.springframework.scheduling.annotation.Scheduled
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

    fun create(topics: KafkaTopics,operation: EventOperation ,body: Any): Event {
        val jsonBody = objectMapper.writeValueAsString(body)
        return Event(topics, operation,jsonBody)
    }

    @Scheduled(fixedDelay = 10000) // 10초에 한 번씩 실행
    @Transactional
    fun outboxPoll() {
        val outboxes = repository.findAll() // 모든 outbox 데이터 조회
        outboxes.forEach { event ->
            kafkaProducer.send(event)
        }
        repository.deleteAll(outboxes) // 조회한 데이터 모두 삭제
    }

}