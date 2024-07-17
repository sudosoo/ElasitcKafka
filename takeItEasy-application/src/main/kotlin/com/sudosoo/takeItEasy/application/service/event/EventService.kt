package com.sudosoo.takeItEasy.application.service.event

import com.sudosoo.takeItEasy.application.common.jpa.JpaService
import com.sudosoo.takeItEasy.application.common.specification.JpaSpecificService
import com.sudosoo.takeItEasy.application.dto.event.CreateEventRequestDto
import com.sudosoo.takeItEasy.application.dto.event.EventResponseDto
import com.sudosoo.takeItEasy.domain.entity.Event
import com.sudosoo.takeItEasy.domain.repository.EventRepository
import com.sudosoo.takeItEasy.domain.repository.common.BaseRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class EventService(
    private val repository: EventRepository,
) :JpaService<Event, Long>, JpaSpecificService<Event, Long> {
    override var jpaRepository: BaseRepository<Event, Long> = repository
    override val jpaSpecRepository: BaseRepository<Event, Long> = repository

    fun create(requestDto: CreateEventRequestDto): EventResponseDto {
        var event = Event.of(requestDto.eventName,requestDto.eventDeadline)
        event = save(event)
        return EventResponseDto(event.id)
    }


}