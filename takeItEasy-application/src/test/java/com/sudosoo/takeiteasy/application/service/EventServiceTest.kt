package com.sudosoo.takeiteasy.application.service

import com.sudosoo.takeItEasy.application.common.jpa.JpaService
import com.sudosoo.takeItEasy.application.dto.event.CreateEventRequestDto
import com.sudosoo.takeItEasy.application.service.event.EventService
import com.sudosoo.takeItEasy.domain.entity.Event
import com.sudosoo.takeItEasy.domain.entity.EventOperation
import com.sudosoo.takeItEasy.domain.repository.DeadLetterRepository
import jakarta.validation.Validation
import jakarta.validation.Validator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.time.LocalDateTime


internal class EventServiceTest{
    @Mock
    lateinit var deadLetterRepository: DeadLetterRepository
    @Mock
    lateinit var jpaService: JpaService<Event, Long>
    @InjectMocks
    lateinit var eventService: EventService

    private lateinit var validator: Validator

    var testEvent = Event(1L, "TestEvent", EventOperation.CREATED ,LocalDateTime.now().plusDays(1) )

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        validator = Validation.buildDefaultValidatorFactory().validator
    }
    @Test
    fun `이벤트가 생성 되어야 한다`() {
        //given
        val requestDto =
            CreateEventRequestDto("TestEvent", "2024-12-31T23:59:59")
        `when`(deadLetterRepository.save(any(Event::class.java))).thenReturn(testEvent)

        //when
        val responseDto = eventService.create(requestDto)

        //then
        assertNotNull( responseDto.eventId)
        verify(deadLetterRepository, times(1)).save(any(Event::class.java))
    }

    @Test
    fun `잘못된 요청이 오면 이벤트 생성에 실패를 해야 한다`() {
        //given
        val requestDto =
            CreateEventRequestDto(null, "")
        val testEvent = (Event(1L, null, EventOperation.CREATED ,LocalDateTime.now().plusDays(1)))
        `when`(deadLetterRepository.save(any(Event::class.java))).thenReturn(testEvent)
        //when
            val validate= validator.validate(requestDto)
        //then
        assertThat(validate).hasSize(2)
        verify(deadLetterRepository, never()).save(any(Event::class.java))
    }


}