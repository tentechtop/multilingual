package com.alphay.boot.official.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfig {


    @Value("${spring.elasticsearch.host1}")
    private String host1;

    @Value("${spring.elasticsearch.port1}")
    private Integer port1;

    @Value("${spring.elasticsearch.host2}")
    private String host2;

    @Value("${spring.elasticsearch.port2}")
    private Integer port2;

    @Value("${spring.elasticsearch.scheme}")
    private String scheme;

    @Bean
    public ElasticsearchClient elasticsearchClient(){
        HttpHost host1 = new HttpHost(this.host1, this.port1, this.scheme);
        HttpHost host2 = new HttpHost(this.host2, this.port2, this.scheme);
        RestClient client = RestClient.builder(host1, host2).build();
        ElasticsearchTransport transport = new RestClientTransport(client, new JacksonJsonpMapper());
        return new ElasticsearchClient(transport);
    }
}
