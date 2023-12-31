package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.coupon.CouponIssuanceRequestDto;
import com.sudosoo.takeiteasy.dto.event.CreateEventRequestDto;
import com.sudosoo.takeiteasy.dto.event.EventResponseDto;
import com.sudosoo.takeiteasy.entity.Coupon;
import com.sudosoo.takeiteasy.entity.Event;
import com.sudosoo.takeiteasy.entity.Member;
import com.sudosoo.takeiteasy.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.border.BevelBorder;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Transactional
@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final MemberService memberService;
    private final CouponService couponService;


    @Override
    public EventResponseDto createdEvent(CreateEventRequestDto requestDto) {
        validateDiscountFields(requestDto);
        LocalDateTime eventDeadLine = LocalDateTime.parse(requestDto.getEventDeadline());
        Coupon coupon;

        if (requestDto.getDiscountRate() != 0) {
            //할인율 적용 쿠폰일때
            coupon = couponService.rateCouponCreate(requestDto);
        } else {
            //할인가격 적용 쿠폰 일때
            coupon = couponService.priceCouponCreate(requestDto);
        }

        Event event = Event.of(requestDto,eventDeadLine, coupon);
        eventRepository.save(event);
        return new EventResponseDto(event.getId(),coupon.getId());
    }

    private static void validateDiscountFields(CreateEventRequestDto requestDto) {
        if ((requestDto.getDiscountRate() == 0 && requestDto.getDiscountPrice() == 0) ||
                (requestDto.getDiscountRate() != 0 && requestDto.getDiscountPrice() != 0)) {
            throw new IllegalArgumentException("discountRate 또는 discountPrice 중 하나만 존재 해야 합니다.");
        }
    }

    @Override
    public void couponIssuance(CouponIssuanceRequestDto requestDto) {
        Event event = eventRepository.findById(requestDto.getEventId())
                .orElseThrow(() -> new IllegalArgumentException("Event is not found"));
        Member member = memberService.getMemberByMemberId(requestDto.getMemberId()) ;

        if (event.isDeadlineExpired()) {
            System.out.println("이벤트가 종료되었습니다.");
        }

        event.decreaseCouponQuantity();
        event.setMember(member);
        eventRepository.save(event);
    }

}
