package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.post.PostDetailResponseDto;
import com.sudosoo.takeiteasy.dto.post.CreatePostRequestDto;
import com.sudosoo.takeiteasy.entity.Post;
import org.springframework.data.domain.Pageable;

public interface PostService {
    Post createdPost(CreatePostRequestDto createPostRequestDto);
    Post getPostByPostId(Long postId);
    PostDetailResponseDto getPostDetailByPostId(Long postId, Pageable pageRequest);
    void createDefaultPosts(int count);
    Post createBatchPosts(int count);
}
