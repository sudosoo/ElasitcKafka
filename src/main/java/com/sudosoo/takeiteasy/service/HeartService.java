package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.HeartRequestDto;

public interface HeartService {

    void creatPostHeart(HeartRequestDto heartRequestDTO);
    void creatCommentHeart(HeartRequestDto heartRequestDTO);
    void postDisHeart(HeartRequestDto heartRequestDTO);
    void commentDisHeart(HeartRequestDto heartRequestDTO);


}
