package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.common.service.JpaService;
import com.sudosoo.takeiteasy.dto.coupon.CouponIssuanceRequestDto;
import com.sudosoo.takeiteasy.dto.event.CreateEventRequestDto;
import com.sudosoo.takeiteasy.dto.event.EventResponseDto;
import com.sudosoo.takeiteasy.entity.Coupon;
import com.sudosoo.takeiteasy.entity.Event;
import com.sudosoo.takeiteasy.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Transactional
@Service
public class EventServiceImpl implements EventService, JpaService<Event,Long> {

    private final EventRepository eventRepository;
    private final CouponService couponService;

    @Override
    public JpaRepository<Event, Long> getJpaRepository() {
        return eventRepository;
    }

    @Override
    public EventResponseDto createdEvent(CreateEventRequestDto requestDto) {
        validateDiscountFields(requestDto);
        LocalDateTime eventDeadLine = LocalDateTime.parse(requestDto.getEventDeadline());
        Coupon coupon;

        coupon = couponService.createCoupon(requestDto);

        Event event = Event.of(requestDto,eventDeadLine, coupon);
        saveModel(event);
        return new EventResponseDto(event.getId(),coupon.getId());
    }

    private static void validateDiscountFields(CreateEventRequestDto requestDto) {
        if ((requestDto.getDiscountRate() == 0 && requestDto.getDiscountPrice() == 0) ||
                (requestDto.getDiscountRate() != 0 && requestDto.getDiscountPrice() != 0)) {
            throw new IllegalArgumentException("discountRate 또는 discountPrice 중 하나만 존재 해야 합니다.");
        }
    }

    @Override
    @Transactional(timeout = 5)
    public void couponIssuance(CouponIssuanceRequestDto requestDto) {
        Event event = eventRepository.findByEventIdForUpdate(requestDto.getEventId())
                .orElseThrow(() -> new IllegalArgumentException("Event is not found"));
        //TODO MemberSetting
        Long memberId = requestDto.getMemberId() ;
        /* 이벤트 종료 시점은 프론트 or 앞단에서 처리 해 준다.
        if (event.isDeadlineExpired()) {
            System.out.println("이벤트가 종료되었습니다.");
        }
        */
        event.decreaseCouponQuantity();
        event.setMember(memberId);
        saveModel(event);
    }

}
