package com.sudosoo.takeItEasy.domain.repository;


import com.sudosoo.takeItEasy.domain.entity.EsPost;
import com.sudosoo.takeItEasy.domain.repository.common.BaseRepository;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PostElasticRepository extends ElasticsearchRepository<EsPost, Long>, BaseRepository<EsPost,Long> {


}