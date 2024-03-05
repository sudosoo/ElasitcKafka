package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.post.*;
import com.sudosoo.takeiteasy.entity.Post;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface PostService {
    PostResponseDto create(CreatePostRequestDto createPostRequestDto) throws ExecutionException, InterruptedException, IOException, TimeoutException;
    Post getByPostId(Long postId);
    PostDetailResponseDto getPostDetailByPostId(Long postId, Pageable pageRequest);
    List<PostTitleOnlyResponseDto> getPaginationPost(Pageable pageable);
    Post createBatchPosts(int count);
    PostResponseDto redisTest(PostRequestDto requestDto);
}
