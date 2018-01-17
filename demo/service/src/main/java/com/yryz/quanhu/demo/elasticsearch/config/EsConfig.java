package com.yryz.quanhu.demo.elasticsearch.config;

import java.net.InetAddress;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.yryz.quanhu.demo.elasticsearch")
public class EsConfig {
	@Value("${spring.elasticsearch.host}")
    private String EsHost;

    @Value("${spring.elasticsearch.port}")
    private int EsPort;

    @Value("${spring.elasticsearch.clusterName}")
    private String EsClusterName;

    @Bean
    public Client client() throws Exception {
        Settings esSettings = Settings.builder()
                .put("cluster.name", EsClusterName)
                .put("client.transport.sniff", true)
                .build();

        PreBuiltTransportClient result = new PreBuiltTransportClient(esSettings);
        return result.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(EsHost), EsPort));
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() throws Exception {
        return new ElasticsearchTemplate(client());
    }
}
