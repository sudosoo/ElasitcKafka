package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.common.service.JpaService;
import com.sudosoo.takeiteasy.dto.message.MessageSendRequestDto;
import com.sudosoo.takeiteasy.entity.Message;
import com.sudosoo.takeiteasy.kafka.KafkaProducer;
import com.sudosoo.takeiteasy.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class  MessageServiceImpl implements MessageService, JpaService<Message,Long> {
    private final KafkaProducer kafkaProducer;
    private final MessageRepository messageRepository;

    @Override
    public JpaRepository<Message, Long> getJpaRepository() {
        return messageRepository;
    }

    @Override
    public void send(MessageSendRequestDto requestDto) {
        //TODO MemberSetting
        Long senderId = requestDto.getMemberId();
        Long receiverId = requestDto.getTargetMemberId();
        Message message = Message.builder()
                .content(requestDto.getContent())
                .senderId(senderId)
                .receiverId(receiverId)
                .messageType(requestDto.getMessageType())
                .build();
        //TODO MemberSetting 유저이름 넣기
        kafkaProducer.sendNotice(receiverId.toString(),message.getContent());
        messageRepository.save(message);
    }


}
