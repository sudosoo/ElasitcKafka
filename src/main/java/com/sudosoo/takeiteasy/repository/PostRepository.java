package com.sudosoo.takeiteasy.repository;

import com.sudosoo.takeiteasy.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PostRepository extends JpaRepository<Post,Long> {


}
