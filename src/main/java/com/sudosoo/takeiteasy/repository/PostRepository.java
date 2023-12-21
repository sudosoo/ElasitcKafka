package com.sudosoo.takeiteasy.repository;

import com.sudosoo.takeiteasy.entity.Post;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long>{
    List<Post> findAllByCategoryId(Long categoryId, PageRequest pageRequest);
}
