package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.post.PostDetailResponseDto;
import com.sudosoo.takeiteasy.dto.post.CreatePostRequestDto;
import com.sudosoo.takeiteasy.dto.post.PostTitleOnlyResponseDto;
import com.sudosoo.takeiteasy.entity.Post;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    Post create(CreatePostRequestDto createPostRequestDto);
    Post getPostByPostId(Long postId);
    PostDetailResponseDto getPostDetailByPostId(Long postId, Pageable pageRequest);
    List<PostTitleOnlyResponseDto> getPaginationPost(Pageable pageable);
    Post createBatchPosts(int count);
}
