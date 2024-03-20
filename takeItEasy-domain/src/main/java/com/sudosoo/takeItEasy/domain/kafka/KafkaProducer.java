package com.sudosoo.takeItEasy.domain.kafka;

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

import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor
public class KafkaProducer {
    @Value("${devsoo.kafka.notice.topic}")
    private String kafkaNoticeTopic;
    @Value("${devsoo.kafka.restapi.request.topic}")
    private String kafkaRestApiRequestTopic;
    @Value("${devsoo.kafka.restapi.reply.topic}")
    private String kafkaRestApiReplyTopic;

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate;

    public void sendNotice(String memberId, String requestMessage) {
        kafkaTemplate.send(kafkaNoticeTopic, memberId ,requestMessage);
    }


    public String replyRecord(Object requestData) throws ExecutionException, InterruptedException, JsonProcessingException {
        ConsumerRecord<String, String> consumerRecord = null;
        try{
            String jsonData = objectMapper.writeValueAsString(requestData);
            ProducerRecord<String, String> record = new ProducerRecord<String, String>(kafkaRestApiRequestTopic,jsonData);
            record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, kafkaRestApiReplyTopic.getBytes()));
            RequestReplyFuture<String, String, String> sendAndReceive = replyingKafkaTemplate.sendAndReceive(record);
            consumerRecord = sendAndReceive.get();
            Objects.requireNonNull(consumerRecord, "데이터 로딩 실패");
        }
        catch (ExecutionException| InterruptedException |JsonProcessingException e ) {
            e.getStackTrace();
        }
        return Objects.requireNonNull(consumerRecord).value();
    }

}
