package com.sudosoo.takeiteasy.entity;

import com.sudosoo.takeiteasy.dto.event.CreateEventRequestDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String eventName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    private int couponQuantity;

    private LocalDateTime eventDeadline;

    @Builder
    private Event(String eventName, Member member, Coupon coupon, int couponQuantity, LocalDateTime eventDeadline) {
        this.eventName = eventName;
        this.member = member;
        this.coupon = coupon;
        this.couponQuantity = couponQuantity;
        this.eventDeadline = eventDeadline;
    }
    public static Event of(CreateEventRequestDto requestDto,LocalDateTime eventDeadline, Coupon coupon){
        return Event.builder()
                .eventName(requestDto.getEventName())
                .coupon(coupon)
                .eventDeadline(eventDeadline)
                .couponQuantity(requestDto.getCouponQuantity())
                .build();

    }
    public boolean isDeadlineExpired() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return eventDeadline.isBefore(currentDateTime);
    }

    public void decreaseCouponQuantity() {
        if (couponQuantity > 0) {
            couponQuantity--;
        } else {
            throw new IllegalStateException("남은 쿠폰 수량이 없습니다.");
        }
    }

    public void addMember(Member member){
        this.member = member;
    }



}
