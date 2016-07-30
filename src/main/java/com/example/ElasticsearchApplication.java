package com.example;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.node.NodeClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.collect.HppcMaps;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

@SpringBootApplication
public class ElasticsearchApplication {
	public static void main(String[] args) {
		SpringApplication.run(ElasticsearchApplication.class, args);
	}
}

@Document(indexName = "data", type = "mydata")
class MyData {
    @Id
    @GeneratedValue
    private Long        id;

    @NotEmpty
    private String      name;

    @NotEmpty
    private String      email;

    @CreatedDate
    private Timestamp   createDate;

    public MyData() {}

    public MyData(String name, String email) {
        this.name = name;
        this.email = email;
        this.createDate = new Timestamp(System.currentTimeMillis());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }
}

/*
@Configuration
class MyConfig {
    @Value("${elasticsearch.hostname:192.168.0.88}")
    private String  hostname;

    @Value("${elasticsearch.port:9200}")
    private Integer port;

    @Value("${elasticsearch.clustername:elasticsearch}")
    private String  clusterName;

    @Bean
    public ElasticsearchTemplate elasticsearchTemplate() {
        //return new ElasticsearchTemplate(getNodeClient());
        return new ElasticsearchTemplate(client());
    }

    @Bean
    public Client client(){
        TransportClient client= new TransportClient();
        TransportAddress address = new InetSocketTransportAddress(hostname, port);
        client.addTransportAddress(address);
        return client;
    }

    @Bean
    public NodeClient getNodeClient() {
        return (NodeClient) nodeBuilder().clusterName(clusterName).local(true).node().client();
    }
}
*/

@Repository
interface MyDataRepository extends ElasticsearchCrudRepository<MyData, Long> {
}

@RestController
class MyController {

    @Autowired
    private MyDataRepository    repository;

    public MyController() {
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public @ResponseBody List<MyData> getMyData() {
        List<MyData> list = new ArrayList<>();
        repository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody MyData getMyData(@PathVariable("id") Long id) {
        return repository.findOne(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public @ResponseBody MyData addMyData(@RequestBody MyData data) {
        return repository.save(data);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public @ResponseBody MyData updateMyData(@PathVariable("id") Long id, @RequestBody MyData data) {
        return repository.save(data);
    }
}
