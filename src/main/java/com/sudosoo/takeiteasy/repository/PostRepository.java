package com.sudosoo.takeiteasy.repository;

import com.sudosoo.takeiteasy.entity.Comment;
import com.sudosoo.takeiteasy.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long>{
    List<Post> findAllByCategoryId(Long categoryId, PageRequest pageRequest);

    @Query("SELECT c FROM Post p JOIN p.comments c WHERE c.id = :postId")
    Page<Comment> findCommentsByPostId(@Param("postId") Long postId, Pageable pageable);
}
