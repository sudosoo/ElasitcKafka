package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.CreatePostRequestDto;
import com.sudosoo.takeiteasy.dto.RelatedPostRequestDto;
import com.sudosoo.takeiteasy.entity.Post;

public interface PostService {
    void creatPost(CreatePostRequestDto createPostRequestDto);
    Post getPostByPostId(Long postId);
    void setMainPostByRelatedPost (RelatedPostRequestDto dto);

}
