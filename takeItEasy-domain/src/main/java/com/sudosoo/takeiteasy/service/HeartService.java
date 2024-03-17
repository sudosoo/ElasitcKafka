package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.heart.CommentHeartRequestDto;
import com.sudosoo.takeiteasy.dto.heart.PostHeartRequestDto;
import com.sudosoo.takeiteasy.entity.Heart;

public interface HeartService {

    Heart createPostHeart(PostHeartRequestDto heartRequestDTO);
    Heart createCommentHeart(CommentHeartRequestDto heartRequestDTO);
    void postDisHeart(PostHeartRequestDto heartRequestDTO);
    void commentDisHeart(CommentHeartRequestDto heartRequestDTO);


}
