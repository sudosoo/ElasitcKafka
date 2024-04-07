package com.sudosoo.takeItEasy.domain.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String couponName;

    @Column(nullable = false)
    private LocalDateTime couponDeadline;

    private int discountRate;

    private long discountPrice;

    private Boolean useCheck = false;

    private Coupon(String couponName, LocalDateTime couponDeadline, int discountRate, long discountPrice, Boolean useCheck) {
        this.couponName = couponName;
        this.couponDeadline = couponDeadline;
        this.discountRate = discountRate;
        this.discountPrice = discountPrice;
        this.useCheck = useCheck;
    }

    private Coupon(Long id, String couponName, LocalDateTime couponDeadline, long discountPrice) {
        this.id = id;
        this.couponName = couponName + discountPrice;
        this.couponDeadline = couponDeadline;
        this.discountPrice = discountPrice;
    }

    public static Coupon testPriceOf(Long id, String couponName, String couponDeadline, Long discountPrice) {
        return new Coupon(id, couponName, LocalDateTime.parse(couponDeadline), discountPrice);
    }

    private Coupon(Long id, String couponName, LocalDateTime couponDeadline, int discountRate) {
        this.id = id;
        this.couponName = couponName + discountRate;
        this.couponDeadline = couponDeadline;
        this.discountRate = discountRate;
    }
    public static Coupon testRateOf(Long id, String couponName, String couponDeadline,Integer discountRate) {
        return new Coupon(id, couponName, LocalDateTime.parse(couponDeadline), discountRate);
    }

    protected Coupon() {
    }

    public static Coupon priceOf(String couponName, String couponDeadline, Long discountPrice) {
        return Coupon.builder()
                .couponName(couponName + discountPrice)
                .couponDeadline(LocalDateTime.parse(couponDeadline))
                .discountPrice(discountPrice)
                .build();
    }

    public static Coupon rateOf(String couponName, String couponDeadline, Integer discountRate) {
        return Coupon.builder()
                .couponName(couponName + discountRate)
                .couponDeadline(LocalDateTime.parse(couponDeadline))
                .discountRate(discountRate)
                .build();
    }

    public static CouponBuilder builder() {
        return new CouponBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public String getCouponName() {
        return this.couponName;
    }

    public LocalDateTime getCouponDeadline() {
        return this.couponDeadline;
    }

    public int getDiscountRate() {
        return this.discountRate;
    }

    public long getDiscountPrice() {
        return this.discountPrice;
    }

    public Boolean getUseCheck() {
        return this.useCheck;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Coupon)) return false;
        final Coupon other = (Coupon) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$couponName = this.getCouponName();
        final Object other$couponName = other.getCouponName();
        if (this$couponName == null ? other$couponName != null : !this$couponName.equals(other$couponName))
            return false;
        final Object this$couponDeadline = this.getCouponDeadline();
        final Object other$couponDeadline = other.getCouponDeadline();
        if (this$couponDeadline == null ? other$couponDeadline != null : !this$couponDeadline.equals(other$couponDeadline))
            return false;
        if (this.getDiscountRate() != other.getDiscountRate()) return false;
        if (this.getDiscountPrice() != other.getDiscountPrice()) return false;
        final Object this$useCheck = this.getUseCheck();
        final Object other$useCheck = other.getUseCheck();
        if (this$useCheck == null ? other$useCheck != null : !this$useCheck.equals(other$useCheck)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Coupon;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $couponName = this.getCouponName();
        result = result * PRIME + ($couponName == null ? 43 : $couponName.hashCode());
        final Object $couponDeadline = this.getCouponDeadline();
        result = result * PRIME + ($couponDeadline == null ? 43 : $couponDeadline.hashCode());
        result = result * PRIME + this.getDiscountRate();
        final long $discountPrice = this.getDiscountPrice();
        result = result * PRIME + (int) ($discountPrice >>> 32 ^ $discountPrice);
        final Object $useCheck = this.getUseCheck();
        result = result * PRIME + ($useCheck == null ? 43 : $useCheck.hashCode());
        return result;
    }

    public static class CouponBuilder {
        private String couponName;
        private LocalDateTime couponDeadline;
        private int discountRate;
        private long discountPrice;
        private Boolean useCheck;

        CouponBuilder() {
        }

        public CouponBuilder couponName(String couponName) {
            this.couponName = couponName;
            return this;
        }

        public CouponBuilder couponDeadline(LocalDateTime couponDeadline) {
            this.couponDeadline = couponDeadline;
            return this;
        }

        public CouponBuilder discountRate(int discountRate) {
            this.discountRate = discountRate;
            return this;
        }

        public CouponBuilder discountPrice(long discountPrice) {
            this.discountPrice = discountPrice;
            return this;
        }

        public CouponBuilder useCheck(Boolean useCheck) {
            this.useCheck = useCheck;
            return this;
        }

        public Coupon build() {
            return new Coupon(this.couponName, this.couponDeadline, this.discountRate, this.discountPrice, this.useCheck);
        }

        public String toString() {
            return "Coupon.CouponBuilder(couponName=" + this.couponName + ", couponDeadline=" + this.couponDeadline + ", discountRate=" + this.discountRate + ", discountPrice=" + this.discountPrice + ", useCheck=" + this.useCheck + ")";
        }
    }
}