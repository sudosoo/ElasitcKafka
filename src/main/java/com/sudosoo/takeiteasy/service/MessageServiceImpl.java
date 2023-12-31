package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.message.MessageSendRequestDto;
import com.sudosoo.takeiteasy.entity.Member;
import com.sudosoo.takeiteasy.entity.Message;
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

    @Override
    public void messageSend(MessageSendRequestDto requestDto) {
        Member sender = memberService.getMemberByMemberId(requestDto.getMemberId());
        Member receiver = memberService.getMemberByMemberName(requestDto.getTargetMemberName());

        Message message = Message.builder()
                .content(requestDto.getContent())
                .sender(sender)
                .receiver(receiver)
                .messageType(requestDto.getMessageType())
                .build();
        messageRepository.save(message);


    }
}
