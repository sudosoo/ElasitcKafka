package com.sudosoo.takeItEasy.domain.repository;

import com.sudosoo.takeItEasy.domain.entity.Comment;
import com.sudosoo.takeItEasy.domain.repository.common.BaseRepository;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;


public interface CommentRepository extends BaseRepository<Comment,Long> {
    @Query("SELECT c FROM Comment c WHERE c.post.id = :postId")
    Page<Comment> findCommentsByPostId(@Param("postId") Long postId, Pageable pageable);

}
