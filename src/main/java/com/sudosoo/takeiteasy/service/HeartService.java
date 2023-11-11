package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.CommentHeartRequestDto;
import com.sudosoo.takeiteasy.dto.PostHeartRequestDto;
import com.sudosoo.takeiteasy.entity.Heart;

public interface HeartService {

    Heart creatPostHeart(PostHeartRequestDto heartRequestDTO);
    Heart creatCommentHeart(CommentHeartRequestDto heartRequestDTO);
    void postDisHeart(PostHeartRequestDto heartRequestDTO);
    void commentDisHeart(CommentHeartRequestDto heartRequestDTO);


}
