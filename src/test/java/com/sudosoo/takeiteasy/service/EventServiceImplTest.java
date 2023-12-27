package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.repository.EventRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

class EventServiceImplTest {
    MemberService memberService = mock(MemberService.class);
    EventRepository eventRepository = mock(EventRepository.class);
    CouponService couponService = mock(CouponServiceImpl.class);
    EventService eventService = new EventServiceImpl(eventRepository,memberService,couponService);
    @Test
    @DisplayName("EventCreateTest")
    void EventCreateTest() throws Exception {
        //given

        //when

        //then
    }

}