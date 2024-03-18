package com.sudosoo.takeItEasy.service

import com.sudosoo.takeItEasy.dto.message.MessageSendRequestDto
import com.sudosoo.takeiteasy.entity.Message
import com.sudosoo.takeiteasy.kafka.KafkaProducer
import com.sudosoo.takeiteasy.repository.MessageRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MessageServiceImpl(
    val kafkaProducer: KafkaProducer,
    val messageRepository: MessageRepository) : MessageService {

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
        messageRepository.save<Message>(message)
    }
}
