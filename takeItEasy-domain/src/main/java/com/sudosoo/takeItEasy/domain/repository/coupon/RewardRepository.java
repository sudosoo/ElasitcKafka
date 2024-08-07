package com.sudosoo.takeItEasy.domain.repository.coupon;

import com.sudosoo.takeItEasy.domain.entity.Reward;
import com.sudosoo.takeItEasy.domain.repository.common.BaseRepository;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RewardRepository extends BaseRepository<Reward,Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM " +
            "Reward r WHERE r.id = :rewardId")
    Optional<Reward> findByIdForUpdate(@Param("rewardId") Long rewardId);
}
