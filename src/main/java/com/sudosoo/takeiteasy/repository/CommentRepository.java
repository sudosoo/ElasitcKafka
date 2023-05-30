package com.sudosoo.takeiteasy.repository;

import com.sudosoo.takeiteasy.entity.Comment;
import com.sudosoo.takeiteasy.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {


}
