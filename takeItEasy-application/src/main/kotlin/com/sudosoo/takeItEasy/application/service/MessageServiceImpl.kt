package com.sudosoo.takeItEasy.application.service

import com.sudosoo.takeItEasy.application.common.service.JpaService
import com.sudosoo.takeItEasy.application.dto.message.MessageSendRequestDto
import com.sudosoo.takeItEasy.domain.entity.Message
import com.sudosoo.takeItEasy.application.kafka.KafkaProducer
import com.sudosoo.takeItEasy.domain.repository.MessageRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MessageServiceImpl(
    val kafkaProducer: KafkaProducer,
    val messageRepository: MessageRepository
) :MessageService, JpaService<Message, Long> {

    override fun getJpaRepository() = messageRepository

    override fun send(requestDto: MessageSendRequestDto) {
        //TODO MemberSetting
        val senderId: Long = requestDto.memberId
        val receiverId: Long = requestDto.targetMemberId
        val message = Message.builder()
            .content(requestDto.content)
            .senderId(senderId)
            .receiverId(receiverId)
            .messageType(requestDto.messageType)
            .build()
        //TODO MemberSetting 유저이름 넣기
        kafkaProducer.sendNotice(receiverId.toString(), message.content)
        save<Message>(message)
    }
}
