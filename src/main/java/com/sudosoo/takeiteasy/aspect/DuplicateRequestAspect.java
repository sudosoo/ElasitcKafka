package com.sudosoo.takeiteasy.aspect;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Aspect
@Component
public class DuplicateRequestAspect
{
    private Set<String> requestSet = Collections.synchronizedSet(new HashSet<>());
    @Pointcut("within(*..*Controller)")
    public void onRequest() {}

    @Around("onRequest()")
    public Object duplicateRequestCheck(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String httpMethod = request.getMethod();

        // GET 메소드인 경우 중복 체크를 하지 않음
        if ("GET".equalsIgnoreCase(httpMethod)) {
            return joinPoint.proceed();
        }

        String requestId = joinPoint.getSignature().toLongString();
        if (requestSet.contains(requestId)) {
            // 중복 요청인 경우
            return handleDuplicateRequest();
        }
        requestSet.add(requestId);
        try {
            // 핵심 로직 실행
            return joinPoint.proceed();
        } finally {
            // 실행 완료 후 삭제
            requestSet.remove(requestId);
        }
    }

        private ResponseEntity<Object> handleDuplicateRequest() {
            // 중복 요청에 대한 응답 처리
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("중복된 요청 입니다");
    }
}
