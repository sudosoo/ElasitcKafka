package com.sudosoo.takeiteasy.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sudosoo.takeiteasy.dto.KafkaRequestMember;
import com.sudosoo.takeiteasy.dto.RequestMessage;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaNoticeProducer {
    @Value("${devsoo.kafka.notice.topic}")
    private String kafkaNoticeTopic;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendNotice(String memberName, String requestMessage) {
        kafkaTemplate.send(kafkaNoticeTopic, memberName ,requestMessage);
    }

    public void send(Object requestMessage) {
        kafkaTemplate.send(kafkaNoticeTopic, requestMessage);
    }


    public void sendCreatePostRequest(KafkaRequestMember data) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String requestData = objectMapper.writeValueAsString(new RequestMessage("createPost", data));
            ProducerRecord<String, String> record = new ProducerRecord<>(kafkaNoticeTopic, requestData);


        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void sendTest(String message) {
        kafkaTemplate.send("TestKafka", message);
    }
}