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
    private final MemberService memberService;
    private final PostService postService;
    private final CommentService commentService;

    //TODO 좋아요 한번 누를시 생성 두번쨰 취소 ? 결정하기
    @Override
    public Heart createdPostHeart(PostHeartRequestDto heartRequestDTO){
        Member member = memberService.getMemberByMemberId(heartRequestDTO.getMemberId());
        Post post = postService.getPostByPostId(heartRequestDTO.getPostId());

        validateHeart(member, post);
        Heart heart = Heart.getPostHeart(post,member);

        return heartRepository.save(heart);

    }

    private void validateHeart(Member member, Post post) {
        heartRepository.findByMemberAndPost(member, post)
                .orElseThrow(() -> new IllegalArgumentException("Duplicated Like !"));
    }

    @Override
    public Heart createdCommentHeart(CommentHeartRequestDto heartRequestDTO)  {
        Member member = memberService.getMemberByMemberId(heartRequestDTO.getMemberId());
        Comment comment  = commentService.getCommentByCommentId(heartRequestDTO.getCommentId());

        validateHeart(member, comment);
        Heart heart = Heart.getCommentHeart(comment,member);

        return heartRepository.save(heart);
    }

    private void validateHeart(Member member, Comment comment) {
        heartRepository.findByMemberAndComment(member, comment)
                .orElseThrow(()-> new IllegalArgumentException("Duplicated Like !"));
    }

    @Override
    public void postDisHeart(PostHeartRequestDto heartRequestDTO) {
        Member member = memberService.getMemberByMemberId(heartRequestDTO.getMemberId());
        Post post = postService.getPostByPostId(heartRequestDTO.getPostId());
        Heart heart = findHeartByMemberAndPostOrComment(member,post);

        heart.unHeartPost();

        heartRepository.delete(heart);
    }

    @Override
    public void commentDisHeart(CommentHeartRequestDto heartRequestDTO) {
        Member member = memberService.getMemberByMemberId(heartRequestDTO.getMemberId());
        Comment comment  = commentService.getCommentByCommentId(heartRequestDTO.getCommentId());
        Heart heart = findHeartByMemberAndPostOrComment(member, comment);

        heart.unHeartComment();

        heartRepository.delete(heart);
    }

    private Heart findHeartByMemberAndPostOrComment(Member member, Object reference) {
        if (reference instanceof Post) {
            return getPostHeart(member,(Post)reference);
        } else if (reference instanceof Comment) {
            return getCommentHeart(member,(Comment)reference);
        } else {
            throw new IllegalArgumentException("Unsupported reference type");
        }
    }
    private Heart getPostHeart(Member m ,Post p){
        return heartRepository.findByMemberAndPost(m,p)
                .orElseThrow(() -> new IllegalArgumentException("Could not find PostHeart id"));
    }
    private Heart getCommentHeart(Member m ,Comment c){
        return heartRepository.findByMemberAndComment(m,c)
                .orElseThrow(() -> new IllegalArgumentException("Could not find CommentHeart id"));
    }

}
