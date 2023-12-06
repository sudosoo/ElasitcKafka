package com.sudosoo.takeiteasy.aspect;

import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Getter
@ToString
public class LogInfo {
    private final String url;
    private final String apiName;
    private final String method;
    private final Map<String, String> header;
    private final String parameters;
    private final String body;
    private final String ipAddress;
    private String exception;

    public LogInfo(String url, String apiName, String method, Map<String, String> header, String parameters, String body, String ipAddress) {
        this.url = url;
        this.apiName = apiName;
        this.method = method;
        this.header = header;
        this.parameters = parameters;
        this.body = body;
        this.ipAddress = ipAddress;
    }

    public void setException(String exception) {
        this.exception = exception;
    }
}

