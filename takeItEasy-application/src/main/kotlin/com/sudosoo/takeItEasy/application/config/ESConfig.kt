package com.sudosoo.takeItEasy.application.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.client.ClientConfiguration
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories
import org.springframework.data.elasticsearch.support.HttpHeaders

@Configuration
@EnableElasticsearchRepositories
class ESConfig(
    @Value("\${spring.elasticsearch.uris}")
    private val url:String,
    @Value("\${spring.elasticsearch.api-key}")
    private val key: String
): ElasticsearchConfiguration(){


    override fun clientConfiguration(): ClientConfiguration {
        val httpHeaders = HttpHeaders()
        httpHeaders.add("Authorization", "ApiKey $key")
        return ClientConfiguration.builder()
            .connectedTo(url)
            .withDefaultHeaders(httpHeaders)
            .build()
    }

}