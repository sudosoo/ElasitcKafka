package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.message.MessageSendRequestDto;
import com.sudosoo.takeiteasy.entity.Member;
import com.sudosoo.takeiteasy.entity.Message;
import com.sudosoo.takeiteasy.kafka.KafkaProducer;
import com.sudosoo.takeiteasy.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class  MessageServiceImpl implements MessageService{
    private final MemberService memberService;
    private final MessageRepository messageRepository;
    private final KafkaProducer kafkaProducer;

    @Override
    public void send(MessageSendRequestDto requestDto) {
        Member sender = memberService.getMemberByMemberId(requestDto.getMemberId());
        Member receiver = memberService.getMemberByMemberId(requestDto.getTargetMemberId());
        Message message = Message.builder()
                .content(requestDto.getContent())
                .sender(sender)
                .receiver(receiver)
                .messageType(requestDto.getMessageType())
                .build();
        kafkaProducer.sendNotice(receiver.getMemberName(),message.getContent());
        messageRepository.save(message);
    }


}
