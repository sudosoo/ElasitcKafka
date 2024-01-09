package com.sudosoo.takeiteasy.kafka;

import com.sudosoo.takeiteasy.dto.message.MessageSendRequestDto;
import com.sudosoo.takeiteasy.dto.notice.NoticeRequestDto;
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

    public void sendNotice(String memberName, String requestMessage) {
        kafkaTemplate.send(kafkaNoticeTopic, memberName ,requestMessage);
    }

    public void sendTest(String message) {
        kafkaTemplate.send("TestKafka", message);
    }
}