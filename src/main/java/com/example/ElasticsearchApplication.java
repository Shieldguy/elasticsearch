package com.example;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.node.NodeClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

@SpringBootApplication
@ComponentScan(basePackages = "com.example")
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class, ElasticsearchAutoConfiguration.class})
public class ElasticsearchApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchApplication.class);

    public static void main(String[] args) {
		SpringApplication.run(ElasticsearchApplication.class, args);
	}
}

@Document(indexName = "data", type = "mydata")
class MyData {
    @Id
    private Long        id;

    //@NotEmpty
    //@Field(type = FieldType.String, index = FieldIndex.analyzed, indexAnalyzer = "german", searchAnalyzer = "german")
    @Field(type = FieldType.String)
    private String      name;

    //@NotEmpty
    @Field(type = FieldType.String)
    private String      email;

    //@CreatedDate
    @Field(type = FieldType.Date)
    private Date        createDate;

    public MyData() {}

    public MyData(String name, String email) {
        this.name = name;
        this.email = email;
        this.createDate = new Date(System.currentTimeMillis());
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.example.repository")
class MyConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyConfig.class);

    @Value("${elasticsearch.hostname:localhost}")
    private String  hostname;

    @Value("${elasticsearch.port:9300}")
    private Integer port;

    @Value("${elasticsearch.clustername:elasticsearch}")
    private String  clusterName;

    /*
    @Bean
    //public ElasticsearchTemplate elasticsearchTemplate() {
    public ElasticsearchTemplate elasticsearchTemplate(Client client) {
        LOGGER.info("MyConfig::elasticsearchTemplate({}) called", client);
        //return new ElasticsearchTemplate(getNodeClient());    // start embedded server
        return new ElasticsearchTemplate(client);
    }
    */
    @Bean
    public ElasticsearchOperations elasticsearchTemplate(Client client) {
        LOGGER.info("MyConfig::elasticsearchTemplate({}) called", client);
        return new ElasticsearchTemplate(client);
    }

    @Bean
    public Client client(){
        LOGGER.info("MyConfig::client called");
        //Settings settings = Settings.settingsBuilder()    // for 2.x
        Settings settings = ImmutableSettings.builder()     // for 1.x
                .put("cluster.name", "elasticsearch")
                .put("cluster.transport.sniff", true)
                .put("cluster.transport.nodes_sampler_interval", "15s")
                .put("cluster.transport.ping_timeout", "25s").build();

        Client client = null;
        try {
            //client = TransportClient.builder().settings(settings).build()   // for 2.x
            //client = new TransportClient(settings)      // for 1.x
            client = new TransportClient()      // for 1.x
                    //.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(hostname), 9300))
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return client;
    }

    //@Bean
    private static NodeClient getNodeClient() {
        LOGGER.info("MyConfig::getNodeClient called");
        return (NodeClient) nodeBuilder().clusterName("elasticsearch").local(true).node().client();
    }
}

//@repository
interface MyDataRepository extends ElasticsearchCrudRepository<MyData, Long> {
}

@RestController
class MyController {

    //@Autowired
    //private EmployeeRepository repository;

    //@Autowired
    //private MyDataRepository    repository;

    @Autowired
    private ElasticsearchTemplate template;

    public MyController() {
    }

    /*
    public void addEmployees() {
        Employee joe = new Employee("01", "Joe", 32);
        Skill javaSkill = new Skill("Java", 10);
        Skill db = new Skill("Oracle", 5);
        joe.setSkills(Arrays.asList(javaSkill, db));
        Employee johnS = new Employee("02", "John S", 32);
        Employee johnP = new Employee("03", "John P", 42);
        Employee sam = new Employee("04", "Sam", 30);

        template.putMapping(Employee.class);
        IndexQuery indexQuery = new IndexQuery();
        indexQuery.setId(joe.getId());
        indexQuery.setObject(joe);
        template.index(indexQuery);
        template.refresh(Employee.class, true);
        repository.save(johnS);
        repository.save(johnP);
        repository.save(sam);
    }
    */

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public @ResponseBody List<MyData> getMyData() {
        List<MyData> list = new ArrayList<>();
        //repository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody MyData getMyData(@PathVariable("id") Long id) {
        //return repository.findOne(id);
        return null;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public @ResponseBody MyData addMyData(@RequestBody MyData data) {
        //return repository.save(data);
        return null;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public @ResponseBody MyData updateMyData(@PathVariable("id") Long id, @RequestBody MyData data) {
        //return repository.save(data);
        return null;
    }
}
