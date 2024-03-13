package com.sudosoo.takeiteasy.repository;

import com.sudosoo.takeiteasy.common.repository.BaseRepository;
import com.sudosoo.takeiteasy.entity.Event;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EventRepository extends BaseRepository<Event,Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT e FROM Event e WHERE e.memberId IS NULL AND e.id = :eventId")
    Optional<Event> findByEventIdForUpdate(@Param("eventId") Long eventId);
}
