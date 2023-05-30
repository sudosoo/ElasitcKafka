package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.HeartRequestDto;

public interface HeartService {

    void postLike(HeartRequestDto heartRequestDTO);
    void commentLike(HeartRequestDto heartRequestDTO);

    void postDisLike(HeartRequestDto heartRequestDTO);
    void commentDisLike(HeartRequestDto heartRequestDTO);


}
