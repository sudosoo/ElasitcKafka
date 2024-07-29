package com.sudosoo.takeItEasy.application.emitter

import com.sudosoo.takeItEasy.application.service.notice.NoticeService
import com.sudosoo.takeItEasy.domain.repository.EmitterRepository
import com.sudosoo.takeItEasy.domain.repository.EmitterRepositoryImpl
import org.springframework.stereotype.Component
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.io.IOException

@Component
class EmitterProducer(
    private val emitterRepository: EmitterRepositoryImpl,
) {
    companion object {
        private const val DEFAULT_TIMEOUT = 60L * 1000 * 60
    }

    fun subscribe(username: String, lastEventId: String): SseEmitter {
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

    private fun sendLostData(lastEventId: String, memberName: String, emitterCreatedTimeByMemberName: String, emitter: SseEmitter) {
        val eventCaches = emitterRepository.findAllEventCacheStartWithByMemberId(memberName)
        eventCaches.entries.stream()
            .filter { entry -> lastEventId.compareTo(entry.key) < 0 }
            .forEach { entry -> sendNotification(emitter, entry.key, emitterCreatedTimeByMemberName, entry.value) }
    }

    private fun hasLostData(lastEventId: String): Boolean {
        return lastEventId.isNotEmpty()
    }

    fun saveEventCache(keyOfReceiverName: String, notification: Any) {
        emitterRepository.saveEventCache(keyOfReceiverName, notification)
    }

    fun findAllEmitterStartWithByMemberId(memberName: String): Map<String, SseEmitter> {
        return emitterRepository.findAllEmitterStartWithByMemberId(memberName)
    }


}