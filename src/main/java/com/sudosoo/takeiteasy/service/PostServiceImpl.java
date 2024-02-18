package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.comment.CommentResposeDto;
import com.sudosoo.takeiteasy.dto.post.CreatePostRequestDto;
import com.sudosoo.takeiteasy.dto.post.PostDetailResponseDto;
import com.sudosoo.takeiteasy.entity.Category;
import com.sudosoo.takeiteasy.entity.Comment;
import com.sudosoo.takeiteasy.entity.Post;
import com.sudosoo.takeiteasy.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final CategoryService categoryService;

    @Override
    public Post createdPost(CreatePostRequestDto requestDto) {
        //TODO MemberSetting
        Long member = requestDto.getMemberId();
        Post post = Post.of(requestDto);
        Category category = categoryService.getCategoryByCategoryId(requestDto.getCategoryId());

        post.setMember(member);
        post.setCategory(category);

        return postRepository.save(post);
    }


    @Override
    public Post getPostByPostId(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Could not found post id : " + postId));
    }

    @Override
    @Transactional(readOnly = true)
    public PostDetailResponseDto getPostDetailByPostId(Long postId, Pageable pageRequest) {
        Post post = postRepository.findById(postId).orElseThrow(()->new IllegalArgumentException("해당 게시물이 존재 하지 않습니다."));
        post.incrementViewCount();
        Page<Comment> comments = postRepository.findCommentsByPostId(postId,pageRequest);

        //TODO MemberSetting 각 코멘트의 유저이름을 찾아와서 넣어주기
        List<CommentResposeDto> responseCommentDtos = comments.stream().map(Comment::toResponseDto).toList();
        return post.toDetailDto(new PageImpl<>(responseCommentDtos));
    }
}