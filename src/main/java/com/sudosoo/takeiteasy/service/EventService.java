package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.event.CreateEventRequestDto;

public interface EventService {

    void createdEvent(CreateEventRequestDto requestDto);


}
