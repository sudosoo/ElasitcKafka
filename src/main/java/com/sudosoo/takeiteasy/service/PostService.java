package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.post.PostDetailResponsetDto;
import com.sudosoo.takeiteasy.dto.post.CreatePostRequestDto;
import com.sudosoo.takeiteasy.dto.post.PostTitleDto;
import com.sudosoo.takeiteasy.entity.Post;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface PostService {
    Post createdPost(CreatePostRequestDto createPostRequestDto);
    Post getPostByPostId(Long postId);
    List<PostTitleDto> getPostByCategoryId(Long categoryId, PageRequest pageRequest);
    PostDetailResponsetDto getPostDetailByPostId(Long postId, PageRequest pageRequest);
}
