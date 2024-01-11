package com.sudosoo.takeiteasy.repository;

import com.sudosoo.takeiteasy.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon,Long> {
}
