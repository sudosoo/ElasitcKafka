package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.CreatePostRequestDto;
import com.sudosoo.takeiteasy.entity.Category;
import com.sudosoo.takeiteasy.entity.Member;
import com.sudosoo.takeiteasy.entity.Post;
import com.sudosoo.takeiteasy.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final CategoryService categoryService;
    private final MemberService memberService;

    public Post createdPost(CreatePostRequestDto createPostRequestDto) {
        Member member = memberService.getMemberByMemberId(createPostRequestDto.getMemberId());
        Post post = Post.of(createPostRequestDto);

        post.setMember(member);
        //카테고리ID가 있으면
        if (createPostRequestDto.getCategoryId() != null){
            return createPostWithCategory(createPostRequestDto, post);
        }

        return postRepository.save(post);
    }

    private Post createPostWithCategory(CreatePostRequestDto createPostRequestDto,Post post) {
        Category category = categoryService.getCategoryByCategoryId(createPostRequestDto.getCategoryId());
        post.setCategory(category);

        return postRepository.save(post);
    }

    public Post getPostByPostId(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Could not found post id : " + postId));
    }
}
