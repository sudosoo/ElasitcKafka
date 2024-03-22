package com.sudosoo.takeItEasy.aop


import com.sudosoo.takeItEasy.application.service.NoticeService
import com.sudosoo.takeItEasy.aop.notice.NotifyInfo


/**
 * NotifyAspect를 사용 하려면 리턴 타입이 NotifyInfo interface를 구현 해야 됌
 * 알림을 보낼 receiverName과 알림이 노출 될 content가 필요 함.
 */
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Aspect
@Component
class NotifyAspect(val noticeService: NoticeService) {

    @Pointcut("@annotation(com.sudosoo.takeItEasy.application.annotation.CustomNotify)")
    fun annotationPointcut() {}

    @AfterReturning(pointcut = "annotationPointcut()", returning = "result")
    fun checkValue(joinPoint: JoinPoint, result: Any?) {
        if (result is ResponseEntity<*>) {
            val body = result.body
            if (body is NotifyInfo) {
                noticeService.send(
                    body.getReceiverName(), body.getNotifyContent()
                )
            }
        }
    }
}
