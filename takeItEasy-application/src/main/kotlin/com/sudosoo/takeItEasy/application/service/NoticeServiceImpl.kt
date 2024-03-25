package com.sudosoo.takeItEasy.application.service

import com.sudosoo.takeItEasy.application.dto.notice.NoticeResponseDto
import com.sudosoo.takeItEasy.domain.entity.Notice
import com.sudosoo.takeItEasy.domain.repository.EmitterRepository
import com.sudosoo.takeItEasy.domain.repository.NoticeRepository
import jakarta.transaction.Transactional
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.io.IOException

@Service
@Transactional
class NoticeServiceImpl(
    private val noticeRepository: NoticeRepository,
    private val emitterRepository: EmitterRepository
) : NoticeService{

    companion object {
        private const val DEFAULT_TIMEOUT = 60L * 1000 * 60
    }

    @KafkaListener(topics = ["\${devsoo.kafka.notice.topic}"], groupId = "notion-group")
    override fun kafkaSend(record: ConsumerRecord<String?, Any?>) {
        val receiverMemberName: String = record.key() ?: ""
        val messageContent: String = record.value()?.toString() ?: ""

        //SSE로 클라이언트에 전송
        send(receiverMemberName, messageContent)
    }

    override fun subscribe(username: String, lastEventId: String): SseEmitter {
        val emitterCreatedTimeByMemberName = makeTimeIncludeMemberName(username)
        val emitter = emitterRepository.save(emitterCreatedTimeByMemberName, SseEmitter(DEFAULT_TIMEOUT))
        emitter.onCompletion { emitterRepository.deleteByEmitterCreatedTimeWithMemberName(emitterCreatedTimeByMemberName) }
        emitter.onTimeout { emitterRepository.deleteByEmitterCreatedTimeWithMemberName(emitterCreatedTimeByMemberName) }

        //더미 이벤트 전송 (연결때 아무것도 보내지 않으면 503 에러)
        val eventId = makeTimeIncludeMemberName(username)
        sendNotification(emitter, eventId, emitterCreatedTimeByMemberName, "EventStream Created. [memberName=$username]")

        // 클라이언트가 미수신한 Event 목록이 존재할 경우 전송하여 Event 유실을 예방
        if (hasLostData(lastEventId)) {
            sendLostData(lastEventId, username, emitterCreatedTimeByMemberName, emitter)
        }

        return emitter
    }


    private fun makeTimeIncludeMemberName(memberName: String): String {
        return memberName + "_" + System.currentTimeMillis()
    }

    fun sendNotification(emitter: SseEmitter, eventCreatedTime: String, emitterId: String, data: Any) {
        try {
            emitter.send(SseEmitter.event()
                .id(eventCreatedTime)
                .name("sse")
                .data(data)
            )
        } catch (exception: IOException) {
            emitterRepository.deleteByEmitterCreatedTimeWithMemberName(emitterId)
        }
    }

    fun hasLostData(lastEventId: String): Boolean {
        return lastEventId.isNotEmpty()
    }

    fun sendLostData(lastEventId: String, memberName: String, emitterCreatedTimeByMemberName: String, emitter: SseEmitter) {
        val eventCaches = emitterRepository.findAllEventCacheStartWithByMemberId(memberName)
        eventCaches.entries.stream()
            .filter { entry -> lastEventId.compareTo(entry.key) < 0 }
            .forEach { entry -> sendNotification(emitter, entry.key, emitterCreatedTimeByMemberName, entry.value) }
    }

    override fun send(receiver: String, content: String) {
        //TODO: 매직넘버 지우고 receiverId를 받아오도록 수정
        val receiverId: Long = 1L
        val notification = noticeRepository.save(createNotification(receiverId, content))

        val eventCreatedTime = "$receiver\"+_+\"${System.currentTimeMillis()}"
        val emitters = emitterRepository.findAllEmitterStartWithByMemberId(receiver)
        emitters.forEach { (keyOfReceiverName, emitter) ->
            emitterRepository.saveEventCache(keyOfReceiverName, notification)
            sendNotification(emitter, eventCreatedTime, keyOfReceiverName, NoticeResponseDto(notification.content))
        }
    }

    private fun createNotification(receiverId: Long, content: String): Notice {
        return Notice.of(receiverId, content)
    }

}

