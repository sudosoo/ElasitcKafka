package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.CommentHeartRequestDto;
import com.sudosoo.takeiteasy.dto.PostHeartRequestDto;

public interface HeartService {

    void creatPostHeart(PostHeartRequestDto heartRequestDTO);
    void creatCommentHeart(CommentHeartRequestDto heartRequestDTO);
    void postDisHeart(PostHeartRequestDto heartRequestDTO);
    void commentDisHeart(CommentHeartRequestDto heartRequestDTO);


}
