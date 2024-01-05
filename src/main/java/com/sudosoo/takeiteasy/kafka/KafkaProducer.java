package com.sudosoo.takeiteasy.kafka;

import com.sudosoo.takeiteasy.dto.message.MessageSendRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducer {

    @Value("${devsoo.kafka.notice.topic}")
    private String kafkaNoticeTopic;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendNotice(MessageSendRequestDto requestDto) {
        kafkaTemplate.send(kafkaNoticeTopic, requestDto);
    }
    public void sendTest(String message) {
        kafkaTemplate.send(kafkaTopic, message);
    }
}