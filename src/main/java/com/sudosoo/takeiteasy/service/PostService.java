package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.CreatePostRequestDto;
import com.sudosoo.takeiteasy.entity.Post;

public interface PostService {
    Post createdPost(CreatePostRequestDto createPostRequestDto);
    Post getPostByPostId(Long postId);
}
