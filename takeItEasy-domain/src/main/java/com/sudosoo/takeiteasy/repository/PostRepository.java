package com.sudosoo.takeiteasy.repository;

import com.sudosoo.takeiteasy.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post,Long>{

    @Query("SELECT p FROM Post p WHERE p.category.id = :categoryId")
    Page<Post> findPostsPaginationByCategoryId(@Param("categoryId")Long categoryId, Pageable pageRequest);
}
