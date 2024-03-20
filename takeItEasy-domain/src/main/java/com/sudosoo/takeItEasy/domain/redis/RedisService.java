package com.sudosoo.takeItEasy.domain.redis;

import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface RedisService {
    void saveReadValue(Object value);
    <T> List<T> findByPaginationPost(String responseDtoName, PageRequest pageable);
    <T> List<T> getValues(String methodName);
    void postRepositoryRedisSynchronization();
}
