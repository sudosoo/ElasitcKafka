package com.sudosoo.takeItEasy.application.service.event

import com.sudosoo.takeItEasy.application.common.jpa.JpaService
import com.sudosoo.takeItEasy.application.common.specification.JpaSpecificService
import com.sudosoo.takeItEasy.application.dto.event.CreateEventRequestDto
import com.sudosoo.takeItEasy.application.dto.event.EventResponseDto
import com.sudosoo.takeItEasy.application.kafka.KafkaProducer
import com.sudosoo.takeItEasy.domain.entity.Event
import com.sudosoo.takeItEasy.domain.entity.EventOperation
import com.sudosoo.takeItEasy.domain.repository.EventRepository
import com.sudosoo.takeItEasy.domain.repository.common.BaseRepository
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.event.KafkaEvent
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class EventService(
    private val repository: EventRepository,
    private val kafkaProducer: KafkaProducer
) :JpaService<Event, Long>, JpaSpecificService<Event, Long> {
    override var jpaRepository: BaseRepository<Event, Long> = repository
    override val jpaSpecRepository: BaseRepository<Event, Long> = repository

    fun publish(operation :EventOperation, body: Any): EventResponseDto {
        val event = Event(operation, body.toString())
        save(event)

        return EventResponseDto(event.id)
    }
    @Scheduled(fixedDelay = 10000) // 10초에 한 번씩 실행
    @Transactional
    fun outboxPoll() {
        val outboxes = repository.findAll() // 모든 outbox 데이터 조회

        outboxes.forEach { outbox ->
            println("outbox: $outbox")
            // outbox 데이터를 이벤트로 변환

            val event = Event(outbox.operation, outbox.body)
            // 이벤트 발행

            kafkaProducer.replyRecord()
            publish(event.operation, event.body)
        }
        repository.deleteAll(outboxes) // 조회한 데이터 모두 삭제
    }

}