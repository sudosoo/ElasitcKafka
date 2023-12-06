package com.sudosoo.takeiteasy.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "${devsoo.kafkalogging.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public String consume(String message) {
        return message;
    }
}
