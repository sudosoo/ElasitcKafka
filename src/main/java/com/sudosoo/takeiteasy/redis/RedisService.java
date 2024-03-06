package com.sudosoo.takeiteasy.redis;

import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface RedisService {
    void saveRedis(Object value);
    <T> List<T> findByPaginationPost(String responseDtoName, PageRequest pageable);
    <T> List<T> getValues(String methodName);
    void postRepositoryToRedis();

}
