package com.sudosoo.takeiteasy.repository;

import com.sudosoo.takeiteasy.entity.Comment;
import com.sudosoo.takeiteasy.entity.Heart;
import com.sudosoo.takeiteasy.entity.Member;
import com.sudosoo.takeiteasy.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart,Long> {
    Optional<Heart> findByMemberAndPost(Member member, Post post);
    Optional<Heart> findByMemberAndComment(Member member, Comment comment);
}
