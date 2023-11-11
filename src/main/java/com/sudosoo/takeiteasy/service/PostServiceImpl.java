package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.CreatePostRequestDto;
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
    private final CategoryRepository categoryRepository;
    private final MemberService memberService;

    /**
     * 게시물 생성dto 받아 게시물을 저장하고,
     * requestDto에 카테고리가 있다면 카테고리에 넣어준다.
     * requestDto에 연관관계를 맺어야 할 게시물이 있다면 연관관계를 맺아준다.
     * */
    public void creatPost(CreatePostRequestDto createPostRequestDto) {
        Member member = memberService.getMemberByMemberId(createPostRequestDto.getMemberId());
        Category category = categoryRepository.findById(createPostRequestDto.getCategoryId())
                .orElseThrow(()->new IllegalArgumentException("해당 카테고리가 존재하지 않습니다"));
        Post post = Post.builder()
                .title(createPostRequestDto.getTitle())
                .content(createPostRequestDto.getContent())
                .member(member)
                .category(category)
                .build();
        postRepository.save(post);
        log.info("New post created : memberName {}, postId{}", member.getUserName(), post.getId());

        if (createPostRequestDto.getRelatedPostId() != null) {
            relatedMainPostByRelatedPostId(post.getId(),createPostRequestDto.getRelatedPostId());
        }
    }

    /**
     * 게시물 id를 받아 게시물엔티티를 반환해주는 메소드
     * */


    /**
     * 게시물 id를 받아 게시물 끼리 연관관계 맺아주는 메소드
     * */
    private void relatedMainPostByRelatedPostId(Long mainPostId,Long relatedPostId) {
        Post mainPost = getPostByPostId(mainPostId);
        Post relatedPost = getPostByPostId(relatedPostId);
        mainPost.setRelatedPost(relatedPost);
        log.info("New Related post has been created : main Post{} , related Post{}" ,mainPostId,relatedPostId);
        postRepository.save(mainPost);
    }

    /**
     * 게시물 id를 받아 게시물 끼리 연관관계 끊어주는 메소드
     * */
    public void removeRelatedMainPostByRelatedPostId(Long mainPostId,Long relatedPostId) {
        Post post = getPostByPostId(mainPostId);
        Post relatedPost = getPostByPostId(relatedPostId);
        post.getRelatedPosts().remove(relatedPost);
        log.info("Deleted related posts: mainPostId{} ,relatedPostId{}" , mainPostId , relatedPostId);
        postRepository.save(post);
    }

    public Post getPostByPostId(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Could not found post id : {}" + postId));
    }
}
