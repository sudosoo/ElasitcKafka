//package com.sudosoo.takeiteasy.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.elasticsearch.client.ClientConfiguration;
//import org.springframework.data.elasticsearch.client.erhlc.ElasticsearchRestTemplate;
//import org.springframework.data.elasticsearch.client.erhlc.RestClients;
//import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
//
//@Configuration
//@Slf4j
//public class ElkConfig extends AbstractElasticsearchConfiguration {
//
//    @Value("${spring.elasticsearch.uris}")
//    private String uris;
//
//    @Primary
//    @Bean
//    public RestHighLevelClient elasticsearchClient() {
//        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
//                .connectedTo(uris)
//                .build();
//
//        return RestClients.create(clientConfiguration).rest();
//    }
//
//    @Bean
//    public ElasticsearchOperations elasticsearchOperations() {
//        return new ElasticsearchRestTemplate(elasticsearchClient());
//    }
//
//}