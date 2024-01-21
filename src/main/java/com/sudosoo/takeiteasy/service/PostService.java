package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.post.CreatePostRequestDto;
import com.sudosoo.takeiteasy.dto.post.PostDetailResponseDto;
import com.sudosoo.takeiteasy.dto.post.PostTitleOnlyResponseDto;
import com.sudosoo.takeiteasy.entity.Post;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    Post createdPost(CreatePostRequestDto createPostRequestDto);
    PostDetailResponseDto getPostDetailByPostId(Long postId, Pageable pageRequest);
    List<PostTitleOnlyResponseDto> getPaginationPostTitle(String title, Pageable pageRequest);
    void createDefaultPosts(int count);
    Post createBatchPosts(int count);
    PostTitleOnlyResponseDto getPostByContent(String content);
}
