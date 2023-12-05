package com.sudosoo.takeiteasy.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topics = "${devsoo.kafkalogging.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String message) {
        log.info(String.format("Consumed message : %s", message));
    }
}
