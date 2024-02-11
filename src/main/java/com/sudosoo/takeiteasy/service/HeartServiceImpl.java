package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.heart.CommentHeartRequestDto;
import com.sudosoo.takeiteasy.dto.heart.PostHeartRequestDto;
import com.sudosoo.takeiteasy.entity.Comment;
import com.sudosoo.takeiteasy.entity.Heart;
import com.sudosoo.takeiteasy.entity.Member;
import com.sudosoo.takeiteasy.entity.Post;
import com.sudosoo.takeiteasy.repository.HeartRepository;
import com.sudosoo.takeiteasy.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class HeartServiceImpl implements HeartService {

    private final HeartRepository heartRepository;
    private final MemberService memberService;
    private final CommentService commentService;
    private final PostRepository postRepository;

    @Override
    public Heart createPostHeart(PostHeartRequestDto heartRequestDTO){
        Member member = memberService.getMemberByMemberId(heartRequestDTO.getMemberId());
        Post post = postRepository.findById(heartRequestDTO.getPostId()).orElseThrow(()->new IllegalArgumentException("해당 게시물이 존재 하지 않습니다."));

        if (existPostHeart(member, post)){
            throw new IllegalArgumentException("Duplicated Like !");
        }
        Heart heart = Heart.getPostHeart(post,member);

        return heartRepository.save(heart);

    }

    private boolean existPostHeart(Member member, Post post) {
        return heartRepository.findByMemberAndPost(member, post).isPresent();
    }

    @Override
    public Heart createCommentHeart(CommentHeartRequestDto heartRequestDTO)  {
        Member member = memberService.getMemberByMemberId(heartRequestDTO.getMemberId());
        Comment comment  = commentService.getCommentByCommentId(heartRequestDTO.getCommentId());


        if (existCommentHeart(member, comment)){
            throw new IllegalArgumentException("Duplicated Like !");
        }
        Heart heart = Heart.getCommentHeart(comment,member);

        return heartRepository.save(heart);
    }

    private boolean existCommentHeart(Member member, Comment comment) {
        return heartRepository.existsByMemberAndComment(member, comment);
    }

    @Override
    public void postDisHeart(PostHeartRequestDto heartRequestDTO) {
        Member member = memberService.getMemberByMemberId(heartRequestDTO.getMemberId());
        Post post = postRepository.findById(heartRequestDTO.getPostId()).orElseThrow(()->new IllegalArgumentException("해당 게시물이 존재 하지 않습니다."));
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
        assert reference instanceof Post || reference instanceof Comment : "Unsupported reference type";

        if (reference instanceof Post) {
            return getPostHeart(member, (Post) reference);
        } else {
            return getCommentHeart(member, (Comment) reference);
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
