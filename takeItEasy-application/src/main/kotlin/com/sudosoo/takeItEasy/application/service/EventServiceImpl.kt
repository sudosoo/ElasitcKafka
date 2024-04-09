package com.sudosoo.takeItEasy.application.service

import com.sudosoo.takeItEasy.application.common.service.JpaService
import com.sudosoo.takeItEasy.application.dto.event.CreateEventRequestDto
import com.sudosoo.takeItEasy.application.dto.event.EventResponseDto
import com.sudosoo.takeItEasy.domain.entity.Event
import com.sudosoo.takeItEasy.domain.repository.EventRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class EventServiceImpl (
    private val eventRepository: EventRepository,
) :EventService , JpaService<Event, Long>{
    override var jpaRepository: JpaRepository<Event, Long> = eventRepository

    override fun create(requestDto: CreateEventRequestDto): EventResponseDto {
        var event = Event.of(requestDto.eventName,requestDto.eventDeadline)
        event = save(event)
        return EventResponseDto(event.id)
    }

}
