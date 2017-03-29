package com.elasticsearch.org.com;

import CustomException.CustomException;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.net.InetAddress;
import java.net.UnknownHostException;

@EnableElasticsearchRepositories
public class ElasticSearchConfiguration {

    public Client client() throws CustomException {
        TransportClient client = new TransportClient.Builder().build();
        try {
            TransportAddress address = new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300);
            client.addTransportAddress(address);
        } catch (UnknownHostException e) {
            throw new CustomException(e.getMessage());
        }
        return client;
    }

    public ElasticsearchOperations elasticsearchTemplate() throws CustomException {
        return new ElasticsearchTemplate(client());
    }
}
