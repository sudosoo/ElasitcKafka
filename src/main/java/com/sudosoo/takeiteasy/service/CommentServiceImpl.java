package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.common.service.JpaService;
import com.sudosoo.takeiteasy.dto.comment.CreateCommentRequestDto;
import com.sudosoo.takeiteasy.entity.Comment;
import com.sudosoo.takeiteasy.entity.Post;
import com.sudosoo.takeiteasy.repository.CommentRepository;
import com.sudosoo.takeiteasy.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService , JpaService<Comment,Long> {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Override
    public JpaRepository<Comment, Long> getJpaRepository() {
        return commentRepository;
    }

    @Override
    public Comment create(CreateCommentRequestDto createCommentRequestDto){
        //TODO MemberSetting
        Long memberId = createCommentRequestDto.getMemberId();
        Post post = postRepository.findById(createCommentRequestDto.getPostId())
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 게시물 입니다 "));
        Comment comment = Comment.of(createCommentRequestDto);
        comment.setPost(post);
        comment.setMember(memberId);

        return save(comment);
    }

    @Override
    public Comment getByCommentId(Long commentId) {
        return findById(commentId);
    }

    @Override
    public Page<Comment> getCommentsByPostId(Long postId, Pageable pageRequest) {
        return commentRepository.findCommentsByPostId(postId,pageRequest);
    }


}
