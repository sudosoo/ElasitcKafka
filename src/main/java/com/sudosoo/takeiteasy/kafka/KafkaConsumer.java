package com.sudosoo.takeiteasy.kafka;

import com.sudosoo.takeiteasy.dto.message.MessageSendRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    @Value("${devsoo.kafkalogging.topic}")
    private String kafkaTopic;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "${devsoo.kafkalogging.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public Object messageConsume(Object message) {
        return message;
    }
}
