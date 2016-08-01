package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@ComponentScan(basePackages = "com.example")
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class, ElasticsearchAutoConfiguration.class})
public class ElasticsearchApplication {
    public static void main(String[] args) {
		SpringApplication.run(ElasticsearchApplication.class, args);
	}
}
