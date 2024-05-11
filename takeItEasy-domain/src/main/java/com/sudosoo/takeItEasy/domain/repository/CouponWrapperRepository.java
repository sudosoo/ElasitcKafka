package com.sudosoo.takeItEasy.domain.repository;

import com.sudosoo.takeItEasy.domain.entity.CouponWrapper;
import com.sudosoo.takeItEasy.domain.repository.common.BaseRepository;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CouponWrapperRepository extends BaseRepository<CouponWrapper,Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM CouponWrapper c WHERE c.id = :couponWrapperId")
    Optional<CouponWrapper> findByIdForUpdate(@Param("couponWrapperId") Long couponWrapperId);
}
