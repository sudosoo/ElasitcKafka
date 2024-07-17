package com.sudosoo.takeItEasy.application.service.notice

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

interface NoticeService {
    fun subscribe(username: String, lastEventId: String): SseEmitter
    fun send(receiver: String, content: String)
    fun kafkaSend(record: ConsumerRecord<String?, Any?>)
}
