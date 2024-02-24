package com.sudosoo.takeiteasy.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sudosoo.takeiteasy.dto.kafkaMemberValidateRequestDto;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor
public class KafkaProducer {
    @Value("${devsoo.kafka.notice.topic}")
    private String kafkaNoticeTopic;
    @Value("${devsoo.kafka.restapi.topic}")
    private String kafkaRestApiTopic;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final ReplyingKafkaTemplate<String, Object, Object> replyingKafkaTemplate;

    public void sendNotice(String memberId, String requestMessage) {
        kafkaTemplate.send(kafkaNoticeTopic, memberId ,requestMessage);
    }

    public void callAPI(ProducerRecord<String, String> dataValue, CompletableFuture<Void> future) {
        try {
            kafkaTemplate.send(kafkaRestApiTopic,dataValue);
            future.complete(null);
        } catch (Exception e) {
            e.printStackTrace();
            // 예외가 발생한 경우 CompletableFuture에 예외를 완료시킴
            future.completeExceptionally(e);
        }
    }

    public Object replyRecord(Object requestData) throws ExecutionException, InterruptedException {
        ProducerRecord<String, Object> record = new ProducerRecord<String, Object>(kafkaRestApiTopic, requestData);
        record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, kafkaRestApiTopic.getBytes()));
        RequestReplyFuture<String, Object, Object> sendAndReceive = replyingKafkaTemplate.sendAndReceive(record);

        SendResult<String, Object> sendResult = sendAndReceive.getSendFuture().get();

        sendResult.getProducerRecord().headers().forEach(header -> System.out.println(header.key() + ":" + header.value().toString()));
        ConsumerRecord<String, Object> consumerRecord = sendAndReceive.get();

        return consumerRecord.value();
    }


    public void produceDtoToKafka(kafkaMemberValidateRequestDto kafkaMemberRequestDto, CompletableFuture<Void> kafkaFuture) {
        CompletableFuture<Void> future = new CompletableFuture<>();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String requestData = objectMapper.writeValueAsString(kafkaMemberRequestDto);
            ProducerRecord<String, String> record = new ProducerRecord<>(kafkaNoticeTopic, requestData);
            callAPI(record, future);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            future.completeExceptionally(e);
        }
    }



}