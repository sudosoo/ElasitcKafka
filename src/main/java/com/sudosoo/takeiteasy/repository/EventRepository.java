package com.sudosoo.takeiteasy.repository;

import com.sudosoo.takeiteasy.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event,Long> {
}
