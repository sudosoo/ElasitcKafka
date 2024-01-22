package com.sudosoo.takeiteasy.aspect.logging;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.ToString;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@SuppressWarnings("unchecked")
@ToString
@Getter
public class RequestApiInfo {

    private String method = null;
    private String url = null;
    private String name = null;
    private final Map<String, String> header = new HashMap<>();
    private final Map<String, String> parameters = new HashMap<>();
    private Map<String, String> body = new HashMap<>();
    private String ipAddress = null;
    private final String dateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"))
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    public RequestApiInfo(JoinPoint joinPoint, Class clazz, ObjectMapper objectMapper) {
        try {
            setRequestDetails();
            setApiInfo(joinPoint, clazz);
            setInputStream(joinPoint, objectMapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setRequestDetails() {
        try {
            final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            setHeader(request);
            setIpAddress(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Request에서 Header 추출
    private void setHeader(HttpServletRequest request) {
        final Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            final String headerName = headerNames.nextElement();
            this.header.put(headerName, request.getHeader(headerName));
        }
    }

    // Request에서 ipAddress 추출
    private void setIpAddress(HttpServletRequest request) {
        this.ipAddress = Optional.of(request)
                .map(httpServletRequest -> Optional.ofNullable(request.getHeader("X-Forwarded-For"))
                        .orElse(Optional.ofNullable(request.getHeader("Proxy-Client-IP"))
                                .orElse(Optional.ofNullable(request.getHeader("WL-Proxy-Client-IP"))
                                        .orElse(Optional.ofNullable(request.getHeader("HTTP_CLIENT_IP"))
                                                .orElse(Optional.ofNullable(request.getHeader("HTTP_X_FORWARDED_FOR"))
                                                        .orElse(request.getRemoteAddr())))))).orElse(null);

    }

    // API 정보 추출
    private void setApiInfo(JoinPoint joinPoint, Class clazz) {
        final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        final Method method = methodSignature.getMethod();
        final RequestMapping requestMapping = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
        final String baseUrl = requestMapping.value()[0];
        Stream.of(GetMapping.class, PutMapping.class, PostMapping.class, DeleteMapping.class, RequestMapping.class)
                .filter(method::isAnnotationPresent)
                .findFirst()
                .ifPresent(mappingClass -> {
                    final Annotation annotation = method.getAnnotation(mappingClass);
                    try {
                        final String[] methodUrl = (String[])mappingClass.getMethod("value").invoke(annotation);
                        this.method = (mappingClass.getSimpleName().replace("Mapping", "")).toUpperCase();
                        this.url = String.format("%s%s", baseUrl, methodUrl.length > 0 ?""+ methodUrl[0] : "");
                        this.name = (String) mappingClass.getMethod("name").invoke(annotation);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    // Body와 Parameters 추출
    private void setInputStream(JoinPoint joinPoint, ObjectMapper objectMapper) {
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        try {
            final CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
            final String[] parameterNames = codeSignature.getParameterNames();
            final Object[] args = joinPoint.getArgs();
            for (int i = 0; i < parameterNames.length; i++) {
                if (parameterNames[i].equals("request")) {
                    this.body = objectMapper.convertValue(args[i],new TypeReference<Map<String, String>>(){});
                } else {
                    String json = objectMapper.writeValueAsString(args[i]);
                    this.parameters.put(parameterNames[i], json);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
