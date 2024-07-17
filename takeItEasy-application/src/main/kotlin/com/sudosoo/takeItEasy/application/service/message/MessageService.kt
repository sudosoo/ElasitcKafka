package com.sudosoo.takeItEasy.application.service.message

import com.sudosoo.takeItEasy.application.common.jpa.JpaService
import com.sudosoo.takeItEasy.application.common.specification.JpaSpecificService
import com.sudosoo.takeItEasy.application.dto.message.MessageSendRequestDto
import com.sudosoo.takeItEasy.application.kafka.KafkaProducer
import com.sudosoo.takeItEasy.domain.entity.Message
import com.sudosoo.takeItEasy.domain.repository.MessageRepository
import com.sudosoo.takeItEasy.domain.repository.common.BaseRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MessageService(
    private val kafkaProducer: KafkaProducer,
    private val messageRepository: MessageRepository
) :JpaService<Message, Long> , JpaSpecificService<Message, Long>{
    override var jpaRepository: BaseRepository<Message, Long> = messageRepository
    override var jpaSpecRepository: BaseRepository<Message, Long> = messageRepository

    fun send(requestDto: MessageSendRequestDto) {
        //TODO MemberSetting
        val senderId: Long = requestDto.memberId
        val receiverId: Long = requestDto.targetMemberId
        val message = Message(
            senderId,
            receiverId,
            requestDto.content,
            requestDto.messageType
        )
        //TODO MemberSetting 유저이름 넣기
        kafkaProducer.sendNotice(receiverId.toString(), message.content)
        save(message)
    }
}
