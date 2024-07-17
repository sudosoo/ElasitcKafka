package com.sudosoo.takeItEasy.presentation.controller

import com.sudosoo.takeItEasy.application.dto.event.CreateEventRequestDto
import com.sudosoo.takeItEasy.application.dto.event.EventResponseDto
import com.sudosoo.takeItEasy.application.service.event.EventService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/event")
class EventController(val eventService: EventService) {
    @PostMapping("/create", name = "createEvent")
    fun createEvent(@RequestBody @Valid requestDto: CreateEventRequestDto): ResponseEntity<EventResponseDto> {
        return ResponseEntity.ok(eventService.create(requestDto))
    }



}
