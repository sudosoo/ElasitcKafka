package com.sudosoo.takeiteasy.common.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CommonService {
    public static <E> boolean checkNotNullData(E e, String message) {
        if (e == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        }
        return true;
    }
}
