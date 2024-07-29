package com.sudosoo.takeItEasy.application.service.notice

import com.sudosoo.takeItEasy.application.commons.jpa.JpaService
import com.sudosoo.takeItEasy.application.commons.specification.JpaSpecificService
import com.sudosoo.takeItEasy.application.dto.notice.NoticeResponseDto
import com.sudosoo.takeItEasy.application.emitter.EmitterProducer
import com.sudosoo.takeItEasy.domain.entity.Notice
import com.sudosoo.takeItEasy.domain.repository.EmitterRepository
import com.sudosoo.takeItEasy.domain.repository.NoticeRepository
import com.sudosoo.takeItEasy.domain.repository.common.BaseRepository
import jakarta.transaction.Transactional
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.io.IOException

@Service
@Transactional
class NoticeService(
    private val noticeRepository: NoticeRepository,
    private val emitterProducer: EmitterProducer,

) :JpaService<Notice,Long>, JpaSpecificService<Notice,Long>{
    override var jpaRepository: BaseRepository<Notice, Long> = noticeRepository
    override var jpaSpecRepository: BaseRepository<Notice, Long> = noticeRepository

    private fun createNotification(receiverId: Long, content: String): Notice {
        return save(Notice.of(receiverId, content))
    }

    fun send(receiver: String, content: String) {
        //TODO: 매직넘버 지우고 receiverId를 받아오도록 수정
        val receiverId = 1L
        val notification = noticeRepository.save(createNotification(receiverId, content))

        val eventCreatedTime = "$receiver\"+_+\"${System.currentTimeMillis()}"
        val emitters = emitterProducer.findAllEmitterStartWithByMemberId(receiver)
        emitters.forEach { (keyOfReceiverName, emitter) ->
            emitterProducer.saveEventCache(keyOfReceiverName, notification)
            emitterProducer.sendNotification(emitter, eventCreatedTime, keyOfReceiverName, NoticeResponseDto(notification.content))
        }
    }

}

