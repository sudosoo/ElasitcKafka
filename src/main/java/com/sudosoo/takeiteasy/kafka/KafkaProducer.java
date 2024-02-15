package com.sudosoo.takeiteasy.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sudosoo.takeiteasy.dto.RequestMessage;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
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

    public void send(ProducerRecord<String, String> dataValue) {
        kafkaTemplate.send(kafkaNoticeTopic, dataValue);
    }


    public void produceDtoToKafka(String targetMethod,Object data) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String requestData = objectMapper.writeValueAsString(new RequestMessage(targetMethod, data));
            ProducerRecord<String, String> record = new ProducerRecord<>(kafkaNoticeTopic, requestData);
            send(record);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void sendTest(String message) {
        kafkaTemplate.send("TestKafka", message);
    }
}