package com.sudosoo.takeItEasy.application.config

import co.elastic.clients.elasticsearch.ElasticsearchClient
import co.elastic.clients.json.jackson.JacksonJsonpMapper
import co.elastic.clients.transport.TransportUtils
import co.elastic.clients.transport.rest_client.RestClientTransport
import org.apache.http.Header
import org.apache.http.HttpHost
import org.apache.http.auth.AuthScope
import org.apache.http.auth.UsernamePasswordCredentials
import org.apache.http.client.CredentialsProvider
import org.apache.http.impl.client.BasicCredentialsProvider
import org.apache.http.message.BasicHeader
import org.elasticsearch.client.Node
import org.elasticsearch.client.RestClient
import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.client.ClientConfiguration
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration
import org.springframework.data.elasticsearch.support.HttpHeaders

@Configuration
//@EnableElasticsearchRepositories
class ESConfig: ElasticsearchConfiguration(){
    private val url:String = "125.128.138.89:9200"
    private val key: String = "dk1nU1BvOEJlNU1DQ1YxSFJQV1k6dU1YcTF3X0tUTDJUOFlXSHJFcWNLdw=="

    override fun clientConfiguration(): ClientConfiguration {
        val headers: MutableList<Header> = mutableListOf()
        val authHeader = BasicHeader("Authorization", "ApiKey $key")
        headers.add(authHeader)
        val httpHeaders = HttpHeaders()
        httpHeaders.add("Authorization", "ApiKey $key")
        return ClientConfiguration.builder()
            .connectedTo(url)
            .withDefaultHeaders(httpHeaders)
            .build()
    }

}