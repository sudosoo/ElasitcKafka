package com.sudosoo.takeItEasy.application.elasticsearch

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.client.ClientConfiguration
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories

@Configuration
@EnableElasticsearchRepositories
abstract class ESConfig() : ElasticsearchConfiguration() {

    @Value("\${spring.elasticsearch.username}")
    private lateinit var userName: String

    @Value("\${spring.elasticsearch.password}")
    private lateinit var password: String

    @Value("\${spring.elasticsearch.uris}")
    private lateinit var esHost: Array<String>

    override fun clientConfiguration(): ClientConfiguration {
        return ClientConfiguration.builder()
            .connectedTo(*esHost)
            .withBasicAuth(userName, password)
            .build()
    }

}