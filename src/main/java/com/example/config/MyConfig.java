package com.example.config;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.node.NodeClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.net.InetAddress;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

/**
 * Created by SimonKim on 8/1/16.
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.example.repository")
public class MyConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyConfig.class);

    @Value("${elasticsearch.hostname:localhost}")
    private String  hostname;

    @Value("${elasticsearch.port:9300}")
    private Integer port;

    @Value("${elasticsearch.clustername:elasticsearch}")
    private String  clusterName;

    @Bean
    public ElasticsearchOperations elasticsearchTemplate(Client client) {
        LOGGER.info("MyConfig::elasticsearchTemplate({}) called", client);
        return new ElasticsearchTemplate(client);
    }

    @Bean
    public Client client(){
        LOGGER.info("MyConfig::client called");

        //Settings settings = ImmutableSettings.builder()     // for 1.x
        Settings settings = Settings.settingsBuilder()    // for 2.x
                .put("cluster.name", clusterName)
                .put("cluster.transport.sniff", true)
                .put("cluster.transport.nodes_sampler_interval", "15s")
                .put("cluster.transport.ping_timeout", "25s").build();

        Client client = null;
        try {
            client = TransportClient.builder().settings(settings).build()   // for 2.x
                    //client = new TransportClient(settings)      // for 1.x
                    //.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(hostname), 9300))
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(hostname), port));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return client;
    }
}
