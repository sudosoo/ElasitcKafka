package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.CreatePostRequestDto;
import com.sudosoo.takeiteasy.dto.RelatedPostRequestDto;
import com.sudosoo.takeiteasy.entity.Member;
import com.sudosoo.takeiteasy.entity.Post;

public interface PostService {
    Post creatPost(CreatePostRequestDto createPostRequestDto);
    Post getPostByPostId(Long postId);
}
