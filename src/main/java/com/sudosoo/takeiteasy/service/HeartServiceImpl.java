package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.common.service.JpaService;
import com.sudosoo.takeiteasy.dto.heart.CommentHeartRequestDto;
import com.sudosoo.takeiteasy.dto.heart.PostHeartRequestDto;
import com.sudosoo.takeiteasy.entity.Comment;
import com.sudosoo.takeiteasy.entity.Heart;
import com.sudosoo.takeiteasy.entity.Post;
import com.sudosoo.takeiteasy.repository.HeartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class HeartServiceImpl implements HeartService, JpaService<Heart,Long> {

    private final HeartRepository heartRepository;
    private final PostService postService;
    private final CommentService commentService;

    @Override
    public JpaRepository<Heart, Long> getJpaRepository() {
        return heartRepository;
    }

    @Override
    public Heart createPostHeart(PostHeartRequestDto requestDto){
        Long memberId = requestDto.getMemberId();
        Post post = postService.getByPostId(requestDto.getPostId());
        if( heartRepository.existsByMemberIdAndPost(memberId, post)){
            throw new IllegalArgumentException("Duplicated Like !");
        }

        Heart heart = Heart.getPostHeart(post,memberId);

        return save(heart);
    }

    @Override
    public Heart createCommentHeart(CommentHeartRequestDto requestDto)  {
        Long memberId = requestDto.getMemberId();
        Comment comment  = commentService.getByCommentId(requestDto.getCommentId());
        if(heartRepository.existsByMemberIdAndComment(memberId, comment)){
            throw new IllegalArgumentException("Duplicated Like !");
        }

        Heart heart = Heart.getCommentHeart(comment,memberId);

        return save(heart);
    }


    @Override
    public void postDisHeart(PostHeartRequestDto heartRequestDTO) {
        Long memberId = heartRequestDTO.getMemberId();
        Post post = postService.getByPostId(heartRequestDTO.getPostId());
        Heart heart = findHeartByMemberAndPostOrComment(memberId,post);

        heart.unHeartPost();

        deleteById(heart.getId());
    }

    @Override
    public void commentDisHeart(CommentHeartRequestDto heartRequestDTO) {
        Long memberId =heartRequestDTO.getMemberId();
        Comment comment  = commentService.getByCommentId(heartRequestDTO.getCommentId());
        Heart heart = findHeartByMemberAndPostOrComment(memberId, comment);

        heart.unHeartComment();
        //TODO deleteById null 체크 뺄지 확인
        deleteById(heart.getId());
    }



    private Heart findHeartByMemberAndPostOrComment(Long memberId, Object reference) {
        if (reference instanceof Post) {
            return getPostHeart(memberId,(Post)reference);
        } else if (reference instanceof Comment) {
            return getCommentHeart(memberId,(Comment)reference);
        } else {
            throw new IllegalArgumentException("Unsupported reference type");
        }
    }

    private Heart getPostHeart(Long memberId ,Post p){
        return heartRepository.findByMemberIdAndPost(memberId,p)
                .orElseThrow(() -> new IllegalArgumentException("Could not find PostHeart id"));
    }

    private Heart getCommentHeart(Long memberId ,Comment c){
        return heartRepository.findByMemberIdAndComment(memberId,c)
                .orElseThrow(() -> new IllegalArgumentException("Could not find CommentHeart id"));
    }

}
