package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.HeartRequestDto;

public interface HeartService {

    void postHeart(HeartRequestDto heartRequestDTO);
    void commentHeart(HeartRequestDto heartRequestDTO);

    void postDisHeart(HeartRequestDto heartRequestDTO);
    void commentDisHeart(HeartRequestDto heartRequestDTO);


}
