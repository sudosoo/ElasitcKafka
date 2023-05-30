package com.sudosoo.takeiteasy.repository;

import com.sudosoo.takeiteasy.entity.Comment;
import com.sudosoo.takeiteasy.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like,Long> {


}
