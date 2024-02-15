package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.KafkaRequestMember;
import com.sudosoo.takeiteasy.dto.comment.CommentResponseDto;
import com.sudosoo.takeiteasy.dto.post.CreatePostRequestDto;
import com.sudosoo.takeiteasy.dto.post.PostDetailResponseDto;
import com.sudosoo.takeiteasy.dto.post.PostTitleOnlyResponseDto;
import com.sudosoo.takeiteasy.entity.Category;
import com.sudosoo.takeiteasy.entity.Comment;
import com.sudosoo.takeiteasy.entity.Member;
import com.sudosoo.takeiteasy.entity.Post;
import com.sudosoo.takeiteasy.kafka.KafkaProducer;
import com.sudosoo.takeiteasy.repository.CommentRepository;
import com.sudosoo.takeiteasy.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CategoryService categoryService;
    private final MemberService memberService;
    private final KafkaProducer kafkaProducer;
    @Override
    public Post createPost(CreatePostRequestDto createPostRequestDto) {
        Member member = memberService.getMemberByMemberId(createPostRequestDto.getMemberId());
        Post post = Post.of(createPostRequestDto);
        Category category = categoryService.getCategoryByCategoryId(createPostRequestDto.getCategoryId());

        post.setMember(member);
        post.setCategory(category);

        return postRepository.save(post);
    }


    @Override
    public PostDetailResponseDto getPostDetailByPostId(Long postId, Pageable pageRequest) {
        Post post = postRepository.findById(postId).orElseThrow(()->new IllegalArgumentException("해당 게시물이 존재 하지 않습니다"));

        post.incrementViewCount();

        Page<Comment> comments = commentRepository.findCommentsByPostId(postId,pageRequest);
        List<CommentResponseDto> responseCommentDtos = comments.stream().map(Comment::toResponseDto).toList();

        return post.toDetailDto(new PageImpl<>(responseCommentDtos));
    }

    @Override
    public List<PostTitleOnlyResponseDto> getPaginationPostTitle(String title, Pageable pageRequest) {
        Page<Post> posts = postRepository.findByTitle(title,pageRequest);
        if(posts.getContent().isEmpty()){
            throw new IllegalArgumentException("해당 게시물이 존재 하지 않습니다");
        }
        List<PostTitleOnlyResponseDto> responseDtos = new ArrayList<>();
        for (Post post: posts) {
            responseDtos.add(post.toTitleOnlyDto());
        }

        return responseDtos;
    }

    @Override
    public void createDefaultPosts(int count) {
        List<Post> postsToSave = new ArrayList<>();
        Member member = memberService.getMemberByMemberId(2L);
        Category category = categoryService.getCategoryByCategoryId(2L);

        for (int i = 0; i < count; i++) {
            postsToSave.add(createDummyPost(i, member, category));
        }

        postRepository.saveAll(postsToSave);
    }

    @Override
    public Post createBatchPosts(int i) {
        Member member = memberService.getMemberByMemberId(2L);
        Category category = categoryService.getCategoryByCategoryId(2L);

        return createDummyPost(i, member, category);
    }


    private Post createDummyPost(int i , Member m , Category c) {
        Post post = Post.of(createDummyPostRequest(i));

        post.setMember(m);
        post.setCategory(c);

        return post;
    }

    private CreatePostRequestDto createDummyPostRequest(int i) {
        return new CreatePostRequestDto("Title 1-" + i, "Content 1-" + i, 2L, 2L);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostTitleOnlyResponseDto> getPaginationPost(Pageable pageRequest) {
        List<PostTitleOnlyResponseDto> responseDtos = new ArrayList<>();
        Page<Post> paginationPost = postRepository.findAll(pageRequest);

        for (Post post : paginationPost ) {
            responseDtos.add(post.toTitleOnlyDto());
        }
        return responseDtos;
    }

}