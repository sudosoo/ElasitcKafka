package com.sudosoo.takeItEasy.domain.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String eventName;

    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    private int couponQuantity;

    private LocalDateTime eventDeadline;

    private Event(String eventName, Coupon coupon, int couponQuantity, LocalDateTime eventDeadline) {
        this.eventName = eventName;
        this.coupon = coupon;
        this.couponQuantity = couponQuantity;
        this.eventDeadline = eventDeadline;
    }

    public Event(Long id, String eventName, Long memberId, Coupon coupon, int couponQuantity, LocalDateTime eventDeadline) {
        this.id = id;
        this.eventName = eventName;
        this.memberId = memberId;
        this.coupon = coupon;
        this.couponQuantity = couponQuantity;
        this.eventDeadline = eventDeadline;
    }

    protected Event() {
    }

    public static Event of(String eventName, int couponQuantity, String eventDeadline, Coupon coupon) {
        return new Event(eventName, coupon, couponQuantity, LocalDateTime.parse(eventDeadline));
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

    public void setMember(Long memberId) {
        this.memberId = memberId;
    }

    public Long getId() {
        return this.id;
    }

    public String getEventName() {
        return this.eventName;
    }

    public Long getMemberId() {
        return this.memberId;
    }

    public Coupon getCoupon() {
        return this.coupon;
    }

    public int getCouponQuantity() {
        return this.couponQuantity;
    }

    public LocalDateTime getEventDeadline() {
        return this.eventDeadline;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Event)) return false;
        final Event other = (Event) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$eventName = this.getEventName();
        final Object other$eventName = other.getEventName();
        if (this$eventName == null ? other$eventName != null : !this$eventName.equals(other$eventName)) return false;
        final Object this$memberId = this.getMemberId();
        final Object other$memberId = other.getMemberId();
        if (this$memberId == null ? other$memberId != null : !this$memberId.equals(other$memberId)) return false;
        final Object this$coupon = this.getCoupon();
        final Object other$coupon = other.getCoupon();
        if (this$coupon == null ? other$coupon != null : !this$coupon.equals(other$coupon)) return false;
        if (this.getCouponQuantity() != other.getCouponQuantity()) return false;
        final Object this$eventDeadline = this.getEventDeadline();
        final Object other$eventDeadline = other.getEventDeadline();
        if (this$eventDeadline == null ? other$eventDeadline != null : !this$eventDeadline.equals(other$eventDeadline))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Event;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $eventName = this.getEventName();
        result = result * PRIME + ($eventName == null ? 43 : $eventName.hashCode());
        final Object $memberId = this.getMemberId();
        result = result * PRIME + ($memberId == null ? 43 : $memberId.hashCode());
        final Object $coupon = this.getCoupon();
        result = result * PRIME + ($coupon == null ? 43 : $coupon.hashCode());
        result = result * PRIME + this.getCouponQuantity();
        final Object $eventDeadline = this.getEventDeadline();
        result = result * PRIME + ($eventDeadline == null ? 43 : $eventDeadline.hashCode());
        return result;
    }

        public String toString() {
            return "Event.EventBuilder(eventName=" + this.eventName + ", memberId=" + this.memberId + ", coupon=" + this.coupon + ", couponQuantity=" + this.couponQuantity + ", eventDeadline=" + this.eventDeadline + ")";
        }

}
