package com.sudosoo.takeItEasy.controller

import com.sudosoo.takeItEasy.dto.coupon.CouponIssuanceRequestDto
import com.sudosoo.takeItEasy.dto.event.CreateEventRequestDto
import com.sudosoo.takeItEasy.dto.event.EventResponseDto
import com.sudosoo.takeItEasy.service.EventService
import com.sudosoo.takeiteasy.dto.coupon.CouponIssuanceRequestDto
import com.sudosoo.takeiteasy.dto.event.CreateEventRequestDto
import com.sudosoo.takeiteasy.dto.event.EventResponseDto
import com.sudosoo.takeiteasy.service.EventService
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
    fun createEvent(@RequestBody @Valid requestDto:CreateEventRequestDto): ResponseEntity<EventResponseDto> {
        return ResponseEntity.ok(eventService.createdEvent(requestDto))
    }

    @PostMapping("/couponIssuance", name = "couponIssuance")
    fun couponIssuance(@Valid @RequestBody requestDto:CouponIssuanceRequestDto): ResponseEntity<Unit> {
        eventService.couponIssuance(requestDto)

        return ResponseEntity.ok().build()
    }

}
