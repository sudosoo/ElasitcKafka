package com.sudosoo.takeiteasy.application.service

import com.sudosoo.takeItEasy.application.common.jpa.JpaService
import com.sudosoo.takeItEasy.application.dto.event.CreateEventRequestDto
import com.sudosoo.takeItEasy.application.service.EventServiceImpl
import com.sudosoo.takeItEasy.domain.entity.Event
import com.sudosoo.takeItEasy.domain.repository.EventRepository
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


internal class EventServiceImplTest{
    @Mock
    lateinit var eventRepository: EventRepository
    @Mock
    lateinit var jpaService: JpaService<Event, Long>
    @InjectMocks
    lateinit var eventService: EventServiceImpl

    private val validator: Validator = Validation.buildDefaultValidatorFactory().validator

    var testEvent = Event(1L, "TestEvent", 1L ,LocalDateTime.now().plusDays(1) )

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }
    @Test
    fun `이벤트가 생성 되어야 한다`() {
        //given
        val requestDto =
            CreateEventRequestDto("TestEvent", "2024-12-31T23:59:59")
        `when`(eventRepository.save(any(Event::class.java))).thenReturn(testEvent)

        //when
        val responseDto = eventService.create(requestDto)

        //then
        assertNotNull( responseDto.eventId)
        verify(eventRepository, times(1)).save(any(Event::class.java))
    }

    @Test
    fun `잘못된 요청이 오면 이벤트 생성에 실패를 해야 한다`() {
        //given
        val requestDto =
            CreateEventRequestDto(null, "")
        val testEvent = (Event(1L, null, 1L ,LocalDateTime.now().plusDays(1)))
        `when`(eventRepository.save(any(Event::class.java))).thenReturn(testEvent)
        //when
            val validate= validator.validate(requestDto)
        //then
        assertThat(validate).hasSize(2)
        verify(eventRepository, never())
    }


}