package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CouponServiceImpl implements CouponService {
    private final EventService eventService;
    private final CouponRepository couponRepository;


}
