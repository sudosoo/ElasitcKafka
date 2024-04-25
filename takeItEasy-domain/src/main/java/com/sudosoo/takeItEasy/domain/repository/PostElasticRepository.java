package com.sudosoo.takeItEasy.domain.repository;

import com.sudosoo.takeItEasy.domain.entity.EsPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface PostElasticRepository extends ElasticsearchRepository<EsPost, Long>{
    Page<EsPost> findByTitle(String title, Pageable pageable);
    Page<EsPost> findByContent(String content, Pageable pageable);
}