package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.post.CreatePostRequestDto;
import com.sudosoo.takeiteasy.dto.post.PostListResponsetDto;
import com.sudosoo.takeiteasy.entity.Category;
import com.sudosoo.takeiteasy.entity.Member;
import com.sudosoo.takeiteasy.entity.Post;
import com.sudosoo.takeiteasy.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final CategoryService categoryService;
    private final MemberService memberService;

    @Override
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

    @Override
    public Post getPostByPostId(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Could not found post id : " + postId));
    }

    @Override
    public List<PostListResponsetDto> getPostByCategoryId(Long categoryId,PageRequest pageRequest) {
        List<PostListResponsetDto> postList = null;
        List<Post> posts = postRepository.findAllByCategoryId(categoryId,pageRequest);

        if (posts.isEmpty()){
            throw new IllegalArgumentException("해당 카테고리에 등록된 게시물이 없습니다.");
        }
        for (Post p: posts) {
            postList.add(p.toDto());
        }
        return postList;
    }

}