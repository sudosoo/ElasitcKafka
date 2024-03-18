package com.sudosoo.takeItEasy.controller;

import com.sudosoo.takeiteasy.dto.coupon.CouponIssuanceRequestDto;
import com.sudosoo.takeiteasy.dto.event.CreateEventRequestDto;
import com.sudosoo.takeiteasy.dto.event.EventResponseDto;
import com.sudosoo.takeiteasy.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    @PostMapping(value = "/create" , name = "createEvent")
    public ResponseEntity<EventResponseDto> createEvent(@Valid @RequestBody CreateEventRequestDto requestDto) {

        return ResponseEntity.ok(eventService.createdEvent(requestDto));
    }

    @PostMapping(value = "/couponIssuance" , name = "couponIssuance")
    public ResponseEntity<Void> couponIssuance(@Valid @RequestBody CouponIssuanceRequestDto requestDto) {
        eventService.couponIssuance(requestDto);

        return ResponseEntity.ok().build();
    }
}
