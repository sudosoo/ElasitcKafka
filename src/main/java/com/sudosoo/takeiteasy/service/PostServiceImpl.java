package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.CreatePostRequestDto;
import com.sudosoo.takeiteasy.entity.Category;
import com.sudosoo.takeiteasy.entity.Member;
import com.sudosoo.takeiteasy.entity.Post;
import com.sudosoo.takeiteasy.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final MemberService memberService;
    private final CategoryService categoryService;

    public void creatPost(CreatePostRequestDto createPostRequestDto) {
        Member member = memberService.getMemberByMemberId(createPostRequestDto.getMemberId());
        Post post = Post.buildEntityFromDto(createPostRequestDto.getTitle(), createPostRequestDto.getContent(), member);

        if (createPostRequestDto.getCategoryId() != null) {
            Category category = categoryService.getCategoryByCategoryId(createPostRequestDto.getCategoryId());
            post.setCategory(category);
        }
        postRepository.save(post);
    }

    public Post getPostByPostId(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Could not found board id : " + postId));
    }

}
