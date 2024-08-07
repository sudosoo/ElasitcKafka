package com.sudosoo.takeItEasy.domain.repository.post;

import com.sudosoo.takeItEasy.domain.entity.Post;
import com.sudosoo.takeItEasy.domain.repository.common.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends BaseRepository<Post, Long>, PostQueryDslRepository {

    @Query("SELECT p FROM Post p WHERE p.category.id = :categoryId")
    Page<Post> findPostsPaginationByCategoryId(@Param("categoryId")Long categoryId, Pageable pageRequest);
}
