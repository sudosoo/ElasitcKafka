package com.sudosoo.takeItEasy.application.elasticsearch

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchProperties
import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.client.ClientConfiguration
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories

@Configuration
@EnableElasticsearchRepositories
abstract class ESConfig(
    var elasticsearchProperties: ElasticsearchProperties
) : ElasticsearchConfiguration() {


    override fun clientConfiguration(): ClientConfiguration {
        return ClientConfiguration.builder()
            .connectedTo(*elasticsearchProperties.uris.toTypedArray())
            .withBasicAuth(elasticsearchProperties.username,elasticsearchProperties.password)
            .build()
    }

}