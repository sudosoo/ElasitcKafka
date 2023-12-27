package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.events.Event;

@RequiredArgsConstructor
@Transactional
@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final MemberService memberService;
    private final CouponService couponService;


}
