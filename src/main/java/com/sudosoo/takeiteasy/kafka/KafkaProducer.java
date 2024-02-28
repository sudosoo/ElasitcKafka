package com.sudosoo.takeiteasy.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor
public class KafkaProducer {
    @Value("${devsoo.kafka.notice.topic}")
    private String kafkaNoticeTopic;
    @Value("${devsoo.kafka.restapi.topic}")
    private String kafkaRestApiTopic;
    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate;

    public void sendNotice(String memberId, String requestMessage) {
        kafkaTemplate.send(kafkaNoticeTopic, memberId ,requestMessage);
    }

    public Object replyRecord(Object requestData) throws ExecutionException, InterruptedException, JsonProcessingException {
        String jsonData = objectMapper.writeValueAsString(requestData);
        ProducerRecord<String, String> record = new ProducerRecord<String, String>(kafkaRestApiTopic,jsonData);
        record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, kafkaRestApiTopic.getBytes()));
        RequestReplyFuture<String, String, String> sendAndReceive = replyingKafkaTemplate.sendAndReceive(record);

        ConsumerRecord<String, String> consumerRecord = sendAndReceive.get();

        return consumerRecord.value();
    }

}
