package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.heart.CommentHeartRequestDto;
import com.sudosoo.takeiteasy.dto.heart.PostHeartRequestDto;
import com.sudosoo.takeiteasy.entity.Heart;

public interface
HeartService {

    Heart createdPostHeart(PostHeartRequestDto heartRequestDTO);
    Heart createdCommentHeart(CommentHeartRequestDto heartRequestDTO);
    void postDisHeart(PostHeartRequestDto heartRequestDTO);
    void commentDisHeart(CommentHeartRequestDto heartRequestDTO);


}
