package com.sudosoo.takeiteasy.redis;

import com.sudosoo.takeiteasy.dto.post.PostResponseDto;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface RedisService {

    List<PostResponseDto> findByPaginationPost(PageRequest pageable);
    List<PostResponseDto> getV(String k);
    void addToRedis();

}
