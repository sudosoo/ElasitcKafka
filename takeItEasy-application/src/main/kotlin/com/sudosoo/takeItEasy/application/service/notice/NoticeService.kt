package com.sudosoo.takeItEasy.application.service.notice

import com.sudosoo.takeItEasy.application.core.commons.jpa.JpaService
import com.sudosoo.takeItEasy.application.core.commons.specification.JpaSpecificService
import com.sudosoo.takeItEasy.application.dto.notice.NoticeResponseDto
import com.sudosoo.takeItEasy.application.emitter.EmitterProducer
import com.sudosoo.takeItEasy.domain.entity.Notice
import com.sudosoo.takeItEasy.domain.repository.common.BaseRepository
import com.sudosoo.takeItEasy.domain.repository.notice.NoticeRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class NoticeService(
    private val noticeRepository: NoticeRepository,
    private val emitterProducer: EmitterProducer,

    ) : JpaService<Notice, Long>, JpaSpecificService<Notice, Long> {
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

