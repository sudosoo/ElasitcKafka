package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.heart.CommentHeartRequestDto;
import com.sudosoo.takeiteasy.dto.heart.PostHeartRequestDto;
import com.sudosoo.takeiteasy.entity.Comment;
import com.sudosoo.takeiteasy.entity.Heart;
import com.sudosoo.takeiteasy.entity.Member;
import com.sudosoo.takeiteasy.entity.Post;
import com.sudosoo.takeiteasy.repository.HeartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class HeartServiceImpl implements HeartService {

    private final HeartRepository heartRepository;
    private final PostService postService;
    private final CommentService commentService;

    @Override
    public Heart createPostHeart(PostHeartRequestDto requestDto){
        //TODO MemberSetting
        Long memberId = requestDto.getMemberId();
        Post post = postService.getPostByPostId(requestDto.getPostId());

        if( heartRepository.existByMemberIdAndPost(memberId, post)){
            throw new IllegalArgumentException("Duplicated Like !");
        }
        Heart heart = Heart.getPostHeart(post,memberId);

        return heartRepository.save(heart);

    }

    @Override
    public Heart createCommentHeart(CommentHeartRequestDto requestDto)  {
        //TODO MemberSetting
        Long memberId = requestDto.getMemberId();
        Comment comment  = commentService.getCommentByCommentId(requestDto.getCommentId());

        if(heartRepository.existByMemberIdAndComment(memberId, comment)){
            throw new IllegalArgumentException("Duplicated Like !");
        }
        Heart heart = Heart.getCommentHeart(comment,memberId);

        return heartRepository.save(heart);
    }


    @Override
    public void postDisHeart(PostHeartRequestDto heartRequestDTO) {
        //TODO MemberSetting
        Long memberId =heartRequestDTO.getMemberId();
        Post post = postService.getPostByPostId(heartRequestDTO.getPostId());
        Heart heart = findHeartByMemberAndPostOrComment(memberId,post);

        heart.unHeartPost();

        heartRepository.delete(heart);
    }

    @Override
    public void commentDisHeart(CommentHeartRequestDto heartRequestDTO) {
        //TODO MemberSetting
        Long memberId =heartRequestDTO.getMemberId();
        Comment comment  = commentService.getCommentByCommentId(heartRequestDTO.getCommentId());
        Heart heart = findHeartByMemberAndPostOrComment(memberId, comment);

        heart.unHeartComment();

        heartRepository.delete(heart);
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
