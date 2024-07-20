package com.sudosoo.takeItEasy.application.kafka

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.sudosoo.takeItEasy.domain.entity.EventOperation
import com.sudosoo.takeItEasy.domain.entity.KafkaTopics
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.header.internals.RecordHeader
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate
import org.springframework.kafka.requestreply.RequestReplyFuture
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.ExecutionException

@Component
class KafkaProducer(
    @Value("\${devsoo.kafka.notice.topic}")
    val kafkaNoticeTopic: String,
    @Value("\${devsoo.kafka.restapi.request.topic}")
    val kafkaRestApiRequestTopic: String,
    @Value("\${devsoo.kafka.restapi.reply.topic}")
    val kafkaRestApiReplyTopic: String,
    val objectMapper: ObjectMapper,
    val kafkaTemplate: KafkaTemplate<String, String>,
    val replyingKafkaTemplate: ReplyingKafkaTemplate<String, String, String>
) {

    fun sendEvent(topic: KafkaTopics, eventOperation: EventOperation, eventPayload: String) {
        val record = ProducerRecord(topic.toString() ,eventOperation.toString(), eventPayload)
        kafkaTemplate.send(record)
    }

    fun sendNotice(memberId: String, requestMessage: String) {
        kafkaTemplate.send(kafkaNoticeTopic, memberId, requestMessage)
    }

    @Throws(ExecutionException::class, InterruptedException::class, JsonProcessingException::class)
    fun replyRecord(requestData: Any): String {
        var consumerRecord: ConsumerRecord<String, String>? = null
        try {
            val jsonData = objectMapper.writeValueAsString(requestData)
            val record = ProducerRecord<String,String>(kafkaRestApiRequestTopic , jsonData)
            record.headers().add(RecordHeader(KafkaHeaders.REPLY_TOPIC, kafkaRestApiReplyTopic.toByteArray()))
            val sendAndReceive: RequestReplyFuture<String, String, String> = replyingKafkaTemplate.sendAndReceive(record)
            consumerRecord = sendAndReceive.get()
            Objects.requireNonNull(consumerRecord, "데이터 로딩 실패")
        } catch (e: ExecutionException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: JsonProcessingException) {
            e.printStackTrace()
        }
        return Objects.requireNonNull(consumerRecord)?.value() ?: ""
    }
}