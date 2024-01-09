package com.sudosoo.takeiteasy.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NoticeService {
    SseEmitter subscribe(String username, String lastEventId);
    void send(String receiver, String content);
    void kafkaSend(ConsumerRecord<String, String> record);

}
