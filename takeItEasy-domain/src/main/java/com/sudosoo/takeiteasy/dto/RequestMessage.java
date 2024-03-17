package com.sudosoo.takeiteasy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RequestMessage {
    private String type;
    private Object data;

}