package com.sudosoo.takeiteasy.controller;

import com.sudosoo.takeiteasy.dto.event.CreateEventRequestDto;
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
    @PostMapping(value = "/create" , name = "createPost")
    public ResponseEntity<Void> createEvent(@Valid @RequestBody CreateEventRequestDto requestDto) {

        eventService.createdEvent(requestDto);

        return ResponseEntity.ok().build();
    }
}
