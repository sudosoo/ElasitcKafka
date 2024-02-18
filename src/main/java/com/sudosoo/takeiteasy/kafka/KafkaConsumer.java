package com.sudosoo.takeiteasy.kafka;

import com.sudosoo.takeiteasy.entity.Member;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    @Value("${devsoo.kafka.notice.topic}")
    private String kafkaTopic;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "${devsoo.kafka.restapi.topic}")
    public void getMember(ConsumerRecord<String, Object> record) {
    }
}
