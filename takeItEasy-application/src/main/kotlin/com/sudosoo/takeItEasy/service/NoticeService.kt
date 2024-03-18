package com.sudosoo.takeItEasy.service

import org.apache.kafka.clients.consumer.ConsumerRecord

interface NoticeService {
    fun subscribe(username: String, lastEventId: String): SseEmitter
    fun send(receiver: String, content: String)
    fun kafkaSend(record: ConsumerRecord<String?, Any?>)
}
