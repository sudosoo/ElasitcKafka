package com.sudosoo.takeItEasy.domain.repository;


import com.sudosoo.takeItEasy.domain.entity.EsPost;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PostElasticRepository extends ElasticsearchRepository<EsPost, Long> {
}