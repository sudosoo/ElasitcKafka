package com.sudosoo.takeItEasy.domain.repository;

import com.sudosoo.takeItEasy.domain.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message,Long> {


}
