package com.sudosoo.takeiteasy.aspect;

import com.sudosoo.takeiteasy.aspect.notice.NotifyInfo;
import com.sudosoo.takeiteasy.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * NotifyAspect를 사용 하려면 리턴 타입이 NotifyInfo interface를 구현 해야 됌
 * 알림을 보낼 receiverName과 알림이 노출 될 content가 필요 함.
 * */
@Async
@Component
@RequiredArgsConstructor
public class NotifyAspect {

    private final NoticeService noticeService;

    @Pointcut("@annotation(com.example.CustomNotify)")
    public void annotationPointcut() {
    }

    @AfterReturning(pointcut = "annotationPointcut()", returning = "result")
    public void checkValue(JoinPoint joinPoint, Object result) throws Throwable {
        if (result instanceof ResponseEntity responseEntity) {
            Object body = responseEntity.getBody();
            if (body instanceof NotifyInfo notifyProxy) {
                noticeService.send(
                        notifyProxy.getReceiverName(),
                        notifyProxy.getContent()
                );
            }
        }
    }


}
