package com.sudosoo.takeItEasy.domain.repository.common;

import com.sudosoo.takeItEasy.domain.entity.Event;
import com.sudosoo.takeItEasy.domain.repository.common.BaseRepository;

public interface DeadLetterRepository extends BaseRepository<Event,Long> {
}

