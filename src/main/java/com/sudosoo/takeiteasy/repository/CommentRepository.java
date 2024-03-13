package com.sudosoo.takeiteasy.repository;

import com.sudosoo.takeiteasy.common.repository.BaseRepository;
import com.sudosoo.takeiteasy.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends BaseRepository<Comment,Long> {
    @Query("SELECT c FROM Comment c WHERE c.post.id = :postId")
    Page<Comment> findCommentsByPostId(@Param("postId") Long postId, Pageable pageable);


}
