package com.stone.config;

import com.stone.sentiment.utils.elasticsearch.ElasticSearchUtils;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

@SpringBootConfiguration
public class ElasticSearchConfig extends AbstractElasticsearchConfiguration {

    @Override
    public RestHighLevelClient elasticsearchClient() {
        final ClientConfiguration configuration = ClientConfiguration.builder()
                .connectedTo("192.168.148.151:9200", "192.168.148.152:9200")
                .build();
        return RestClients.create(configuration).rest();
    }

    @Bean
    public ElasticsearchRestTemplate elasticsearchRestTemplate(){
        return new ElasticsearchRestTemplate(elasticsearchClient());
    }

    @Bean
    public ElasticSearchUtils elasticSearchUtils(){
        return new ElasticSearchUtils();
    }



}
