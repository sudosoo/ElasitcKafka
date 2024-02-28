package com.sudosoo.takeiteasy.kafka;

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
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor
public class KafkaProducer {
    @Value("${devsoo.kafka.notice.topic}")
    private String kafkaNoticeTopic;
    @Value("${devsoo.kafka.restapi.topic}")
    private String kafkaRestApiTopic;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final ReplyingKafkaTemplate<String, Object, String> replyingKafkaTemplate;

    public void sendNotice(String memberId, String requestMessage) {
        kafkaTemplate.send(kafkaNoticeTopic, memberId ,requestMessage);
    }

    public Object replyRecord(kafkaMemberValidateRequestDto requestData) throws ExecutionException, InterruptedException {

        ProducerRecord<String, Object> record = new ProducerRecord<String, Object>(kafkaRestApiTopic,requestData);
        record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, "replyingTopic".getBytes()));
        RequestReplyFuture<String, Object, String> sendAndReceive = replyingKafkaTemplate.sendAndReceive(record);

        ConsumerRecord<String, String> consumerRecord = sendAndReceive.get();

        return consumerRecord.value();
    }

}
