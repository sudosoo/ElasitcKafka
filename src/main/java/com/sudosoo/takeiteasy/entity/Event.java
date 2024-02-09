package com.sudosoo.takeiteasy.entity;

import com.sudosoo.takeiteasy.dto.event.CreateEventRequestDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Getter
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_name")
    private String eventName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private int couponQuantity;

    private LocalDateTime eventDeadline;

    @Builder
    private Event(String eventName, Member member, int couponQuantity, LocalDateTime eventDeadline) {
        this.eventName = eventName;
        this.member = member;
        this.couponQuantity = couponQuantity;
        this.eventDeadline = eventDeadline;
    }
    public static Event of(CreateEventRequestDto requestDto){
        return Event.builder()
                .eventName(requestDto.getEventName())
                .eventDeadline(LocalDateTime.parse(requestDto.getEventDeadline()))
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
