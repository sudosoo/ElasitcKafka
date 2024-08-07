package com.sudosoo.takeItEasy.domain.repository.heart;

import com.sudosoo.takeItEasy.domain.entity.Comment;
import com.sudosoo.takeItEasy.domain.entity.Heart;
import com.sudosoo.takeItEasy.domain.entity.Post;
import com.sudosoo.takeItEasy.domain.repository.common.BaseRepository;

import java.util.Optional;

public interface HeartRepository extends BaseRepository<Heart,Long> {
    Optional<Heart> findByMemberIdAndPost(Long memberId, Post post);
    Optional<Heart> findByMemberIdAndComment(Long memberId, Comment comment);
    boolean existsByMemberIdAndPost(Long memberId, Post post);
    boolean existsByMemberIdAndComment(Long memberId, Comment comment);
}
