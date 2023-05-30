package com.sudosoo.takeiteasy.service;

import com.sudosoo.takeiteasy.dto.HeartRequestDto;
import com.sudosoo.takeiteasy.entity.Comment;
import com.sudosoo.takeiteasy.entity.Heart;
import com.sudosoo.takeiteasy.entity.Member;
import com.sudosoo.takeiteasy.entity.Post;
import com.sudosoo.takeiteasy.repository.HeartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final HeartRepository heartRepository;
    private final MemberService memberService;
    private final PostService postService;
    private final CommentService commentService;

    @Transactional
    public void postLike(HeartRequestDto heartRequestDTO){

        Member member = memberService.getMemberByMemberId(heartRequestDTO.getMemberId());
        Post post = postService.getPostByPostId(heartRequestDTO.getPostId());

        // 이미 좋아요되어있으면 에러 반환
        validateLike(heartRepository.findByMemberAndPost(member, post));
        var heart = Heart.postLike(post,member);
        
        heartRepository.save(heart);
    }

    @Transactional
    public void commentLike(HeartRequestDto heartRequestDTO)  {

        Member member = memberService.getMemberByMemberId(heartRequestDTO.getMemberId());
        Comment comment  = commentService.getCommentByCommentId(heartRequestDTO.getCommentId());

        // 이미 좋아요되어있으면 에러 반환
        validateLike(heartRepository.findByMemberAndComment(member, comment));
        var heart = Heart.commentLike(comment,member);
        
        heartRepository.save(heart);
    }



    @Transactional
    public void postDisLike(HeartRequestDto heartRequestDTO) {
        Member member = memberService.getMemberByMemberId(heartRequestDTO.getMemberId());
        Post post = postService.getPostByPostId(heartRequestDTO.getPostId());
        Heart heart = findHeartByMemberAndReference(member,post);

        heartRepository.delete(heart);
    }

    @Transactional
    public void commentDisLike(HeartRequestDto heartRequestDTO) {
        Member member = memberService.getMemberByMemberId(heartRequestDTO.getMemberId());
        Comment comment  = commentService.getCommentByCommentId(heartRequestDTO.getCommentId());
        Heart heart = findHeartByMemberAndReference(member, comment);

        heartRepository.delete(heart);
    }

    private Heart findHeartByMemberAndReference(Member member, Object reference) {
        if (reference instanceof Post) {
            return heartRepository.findByMemberAndPost(member, (Post) reference)
                    .orElseThrow(() -> new IllegalArgumentException("Could not find heart id"));
        } else if (reference instanceof Comment) {
            return heartRepository.findByMemberAndComment(member, (Comment) reference)
                    .orElseThrow(() -> new IllegalArgumentException("Could not find heart id"));
        } else {
            throw new IllegalArgumentException("Unsupported reference type");
        }
    }

    private void validateLike(Optional<Heart> heartRepository) {
        if (heartRepository.isPresent()) {
            throw new IllegalArgumentException("Duplicated Like !");
        }
    }

   

}
