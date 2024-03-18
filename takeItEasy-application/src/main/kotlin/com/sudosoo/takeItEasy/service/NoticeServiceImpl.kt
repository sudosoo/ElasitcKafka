package com.sudosoo.takeItEasy.service

import com.sudosoo.takeItEasy.dto.notice.NoticeResponseDto
import com.sudosoo.takeiteasy.entity.Notice
import com.sudosoo.takeiteasy.repository.EmitterRepository
import com.sudosoo.takeiteasy.repository.NoticeRepository
import jakarta.transaction.Transactional
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.io.IOException
import java.util.function.BiConsumer

@Service
@Transactional
class NoticeServiceImpl(
    private val noticeRepository: NoticeRepository,
    private val emitterRepository: EmitterRepository ) : NoticeService {

    @KafkaListener(topics = arrayOf("\${devsoo.kafka.notice.topic}"), groupId = "notion-group")
    override fun kafkaSend(record: ConsumerRecord<String?, Any?>) {
        val receiverMemberName: String = record.key() ?: ""
        val messageContent: String = record.value()?.toString() ?: ""

        //SSE로 클라이언트에 전송
        send(receiverMemberName, messageContent)
    }

    override fun subscribe(memberName: String, lastEventId: String): SseEmitter {
        val emitterCreatedTimeByMemberName = makeTimeIncludeMemberName(memberName)
        val emitter: SseEmitter = emitterRepository.save(emitterCreatedTimeByMemberName, SseEmitter(DEFAULT_TIMEOUT))
        emitter.onCompletion(Runnable {
            emitterRepository.deleteByEmitterCreatedTimeWithMemberName(
                emitterCreatedTimeByMemberName
            )
        })
        emitter.onTimeout(Runnable {
            emitterRepository.deleteByEmitterCreatedTimeWithMemberName(
                emitterCreatedTimeByMemberName
            )
        })

        //더미 이벤트 전송 (연결때 아무것도 보내지 않으면 503 에러)
        val eventId = makeTimeIncludeMemberName(memberName)
        sendNotification(
            emitter,
            eventId,
            emitterCreatedTimeByMemberName,
            "EventStream Created. [memberName=$memberName]"
        )

        // 클라이언트가 미수신한 Event 목록이 존재할 경우 전송하여 Event 유실을 예방
        if (hasLostData(lastEventId)) {
            sendLostData(lastEventId, memberName, emitterCreatedTimeByMemberName, emitter)
        }

        return emitter
    }

    private fun makeTimeIncludeMemberName(memberName: String): String {
        return memberName + "_" + System.currentTimeMillis()
    }

    private fun sendNotification(emitter: SseEmitter, eventCreatedTime: String, emitterId: String, data: Any) {
        try {
            emitter.send(
                SseEmitter.event()
                    .id(eventCreatedTime)
                    .name("sse")
                    .data(data)
            )
        } catch (exception: IOException) {
            emitterRepository.deleteByEmitterCreatedTimeWithMemberName(emitterId)
        }
    }

    private fun hasLostData(lastEventId: String): Boolean {
        return lastEventId.isNotEmpty()
    }

    private fun sendLostData(
        lastEventId: String,
        memberName: String,
        emitterCreatedTimeByMemberName: String,
        emitter: SseEmitter
    ) {
        val eventCaches: Map<String, Any> =
            emitterRepository.findAllEventCacheStartWithByMemberId(memberName)
        eventCaches.entries.stream()
            .filter { entry: Map.Entry<String, Any> -> lastEventId < entry.key }
            .forEach { entry: Map.Entry<String, Any> ->
                sendNotification(
                    emitter,
                    entry.key,
                    emitterCreatedTimeByMemberName,
                    entry.value
                )
            }
    }

    override fun send(receiverName: String, content: String) {
        //TODO 유저 이름으로 유저 id 받아오기 (매직 넘버 수정)
        val receiverId = 1L
        val notification: Notice = noticeRepository.save(createNotification(receiverId, content))

        val eventCreatedTime = receiverName + "_" + System.currentTimeMillis()
        val emitters: Map<String, SseEmitter> = emitterRepository.findAllEmitterStartWithByMemberId(receiverName)
        emitters.forEach(
            BiConsumer<String, SseEmitter> { keyOfReceiverName: String, emitter: SseEmitter ->
                emitterRepository.saveEventCache(keyOfReceiverName, notification)
                sendNotification(emitter, eventCreatedTime, keyOfReceiverName, NoticeResponseDto(notification.content))
            }
        )
    }

    private fun createNotification(receiverId: Long, content: String): Notice {
        return Notice.of(receiverId, content)
    }

    companion object {
        private const val DEFAULT_TIMEOUT = 60L * 1000 * 60
    }
}

