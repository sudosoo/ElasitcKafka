package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.notice.NoticeResponseDto;
import com.sudosoo.takeiteasy.entity.Member;
import com.sudosoo.takeiteasy.entity.Notice;
import com.sudosoo.takeiteasy.repository.EmitterRepository;
import com.sudosoo.takeiteasy.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticeServiceImpl implements NoticeService{
    private final MemberService memberService;
    private final NoticeRepository noticeRepository;
    private final EmitterRepository emitterRepository;
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;


    @KafkaListener(topics = "${devsoo.kafka.notice.topic}")
    public void kafkaSend(ConsumerRecord<String, Object> record) {
        String receiverMemberName = record.key();
        String messageContent = record.value().toString();

        //SSE 로 클라이언트로 보내기
        send(receiverMemberName,messageContent);
    }


    public SseEmitter subscribe(String memberName, String lastEventId) {
        String emitterCreatedTimeByMemberName = makeTimeIncludeMemberName(memberName);
        SseEmitter emitter = emitterRepository.save(emitterCreatedTimeByMemberName, new SseEmitter(DEFAULT_TIMEOUT));
        emitter.onCompletion(() -> emitterRepository.deleteByEmitterCreatedTimeWithMemberName(emitterCreatedTimeByMemberName));
        emitter.onTimeout(() -> emitterRepository.deleteByEmitterCreatedTimeWithMemberName(emitterCreatedTimeByMemberName));

        // 503 에러를 방지하기 위한 더미 이벤트 전송
        String eventId = makeTimeIncludeMemberName(memberName);
        sendNotification(emitter, eventId, emitterCreatedTimeByMemberName, "EventStream Created. [memberName=" + memberName + "]");

        // 클라이언트가 미수신한 Event 목록이 존재할 경우 전송하여 Event 유실을 예방
        if (hasLostData(lastEventId)) {
            sendLostData(lastEventId, memberName, emitterCreatedTimeByMemberName, emitter);
        }

        return emitter;
    }

    private String makeTimeIncludeMemberName(String memberName) {
        return memberName + "_" + System.currentTimeMillis();
    }

    private void sendNotification(SseEmitter emitter, String eventCreatedTime, String emitterId, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(eventCreatedTime)
                    .name("sse")
                    .data(data)
            );
        } catch (IOException exception) {
            emitterRepository.deleteByEmitterCreatedTimeWithMemberName(emitterId);
        }
    }

    private boolean hasLostData(String lastEventId) {
        return !lastEventId.isEmpty();
    }

    private void sendLostData(String lastEventId, String memberName, String emitterCreatedTimeByMemberName, SseEmitter emitter) {
        Map<String, Object> eventCaches = emitterRepository.findAllEventCacheStartWithByMemberId(String.valueOf(memberName));
        eventCaches.entrySet().stream()
                .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                .forEach(entry -> sendNotification(emitter, entry.getKey(), emitterCreatedTimeByMemberName, entry.getValue()));
    }

    public void send(String receiverName, String content) {
        Member receiver = memberService.getMemberByMemberName(receiverName);
        Notice notification = noticeRepository.save(createNotification(receiver, content));

        String eventCreatedTime = receiverName + "_" + System.currentTimeMillis();
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByMemberId(receiverName);
        emitters.forEach(
                (keyOfReceiverName, emitter) -> {
                    emitterRepository.saveEventCache(keyOfReceiverName, notification);
                    sendNotification(emitter, eventCreatedTime, keyOfReceiverName, new NoticeResponseDto(notification));
                }
        );
    }

    private Notice createNotification(Member receiver , String content) {
        return Notice.of(receiver,content);
    }


}
