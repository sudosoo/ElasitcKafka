package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.CreatePostRequestDto;
import com.sudosoo.takeiteasy.dto.RelatedPostRequestDto;
import com.sudosoo.takeiteasy.entity.Category;
import com.sudosoo.takeiteasy.entity.Member;
import com.sudosoo.takeiteasy.entity.Post;
import com.sudosoo.takeiteasy.repository.CategoryRepository;
import com.sudosoo.takeiteasy.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final CategoryService categoryService;
    private final MemberService memberService;

    /**
     * 게시물 생성dto 받아 게시물을 저장하고,
     * requestDto에 카테고리가 있다면 카테고리에 넣어준다.
     * requestDto에 연관관계를 맺어야 할 게시물이 있다면 연관관계를 맺어준다.
     * */
    public void creatPost(CreatePostRequestDto createPostRequestDto) {
        Member member = memberService.getMemberByMemberId(createPostRequestDto.getMemberId());
        Post defaultPost = Post.builder()
                .title(createPostRequestDto.getTitle())
                .content(createPostRequestDto.getContent())
                .member(member)
                .build();

        //카테고리ID가 있으면
        if (createPostRequestDto.getCategoryId() != null){
            createPostWithCategory(createPostRequestDto, member);
        }

        postRepository.save(defaultPost);
        log.info("New post created : memberName {}, postId{}", member.getUserName(), defaultPost.getId());
    }

    private void createPostWithCategory(CreatePostRequestDto createPostRequestDto, Member member) {
        Category category = categoryService.getCategoryByCategoryId(createPostRequestDto.getCategoryId());
        Post post = Post.builder()
                .title(createPostRequestDto.getTitle())
                .content(createPostRequestDto.getContent())
                .member(member)
                .category(category)
                .build();

        postRepository.save(post);
        log.info("New post created : memberName {}, postId{}", member.getUserName(), post.getId());
    }

    /**
     * 게시물 id를 받아 게시물 끼리 연관관계 맺아주는 메소드
     * */
    public void setMainPostByRelatedPost(RelatedPostRequestDto dto) {
        Post mainPost = getPostByPostId(dto.getPostId());
        Post relatedPost = getPostByPostId(dto.getRelatedPostId());

        mainPost.setRelatedPost(relatedPost);

        postRepository.save(mainPost);
        log.info("New related post created : main Post{} , related Post{}" ,mainPost,relatedPost);
    }

    /**
     * 게시물 id를 받아 게시물 끼리 연관관계 끊어주는 메소드
     * */
    public void removeRelatedMainPostByRelatedPostId(Long mainPostId,Long relatedPostId) {
        Post post = getPostByPostId(mainPostId);
        Post relatedPost = getPostByPostId(relatedPostId);

        post.getRelatedPosts().remove(relatedPost);

        postRepository.save(post);
        log.info("Deleted related posts: mainPostId{} ,relatedPostId{}" , mainPostId , relatedPostId);
    }

    public Post getPostByPostId(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Could not found post id : {}" + postId));
    }
}
