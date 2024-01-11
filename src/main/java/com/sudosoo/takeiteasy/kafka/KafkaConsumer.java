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

    @Value("${devsoo.kafka.notice.topic}")
    private String kafkaTopic;

    private final KafkaTemplate<String, Object> kafkaTemplate;



}
