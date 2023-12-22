package com.sudosoo.takeiteasy.repository;

import com.sudosoo.takeiteasy.entity.Category;
import com.sudosoo.takeiteasy.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    @Query("SELECT p FROM Category c JOIN c.posts p WHERE c.id = :categoryId")
    Page<Post> findPostsByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);
}
