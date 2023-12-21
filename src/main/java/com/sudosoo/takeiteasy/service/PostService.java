package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.post.CreatePostRequestDto;
import com.sudosoo.takeiteasy.dto.post.PostListResponsetDto;
import com.sudosoo.takeiteasy.entity.Post;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface PostService {
    Post createdPost(CreatePostRequestDto createPostRequestDto);
    Post getPostByPostId(Long postId);
    List<PostListResponsetDto> getPostByCategoryId(Long categoryId, PageRequest pageRequest);
}
