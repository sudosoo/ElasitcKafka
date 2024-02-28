package com.sudosoo.takeiteasy.repository;

import com.sudosoo.takeiteasy.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message,Long> {


}
