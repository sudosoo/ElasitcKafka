package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.message.MessageSendRequestDto;
import com.sudosoo.takeiteasy.dto.message.NotifyRequestDto;
import com.sudosoo.takeiteasy.entity.Member;
import com.sudosoo.takeiteasy.entity.Message;
import com.sudosoo.takeiteasy.entity.Notify;
import com.sudosoo.takeiteasy.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class  MessageServiceImpl implements MessageService{
    private final MemberService memberService;
    private final MessageRepository messageRepository;

    @Override
    public void messageSend(MessageSendRequestDto requestDto) {
        Member sender = memberService.getMemberByMemberId(requestDto.getMemberId());
        Member receiver = memberService.getMemberByMemberId(requestDto.getTargetMemberName());
        Message message = Message.builder()
                .content(requestDto.getContent())
                .sender(sender)
                .receiver(receiver)
                .messageType(requestDto.getMessageType())
                .build();

        messageRepository.save(message);
    }


    @KafkaListener(topics = "${devsoo.kafka.notice.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void notifyMembers(NotifyRequestDto requestDto) {
        // 모든 회원을 가져옴
        List<Member> members = memberService.findAllMembers();
        if(members.isEmpty()){
            throw new IllegalArgumentException("member is not found");
        }

        members.parallelStream().forEach(member -> sendAndSaveNotify(requestDto, member));
    }

    private void sendAndSaveNotify(NotifyRequestDto requestDto, Member member) {
        Notify notify = Notify.builder()
                .content(requestDto.getContent())
                .receiver(member)
                .build();
        messageRepository.save(notify);
    }

}
