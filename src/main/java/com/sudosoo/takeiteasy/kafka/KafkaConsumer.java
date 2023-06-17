package com.sudosoo.takeiteasy.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topics = "takeiteasy-logs", groupId = "devsoo")
    public void consume(String message) throws IOException {
        log.info(String.format("Consumed message : %s", message));
    }
}
