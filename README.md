# elasticsearch
Test project for Elasticsearch with Spring Boot

### Making Environment 

1. Install Elasticsearch 2.3.4 
    - In my case, I just pulled elasticsearch Docker image from Docker hub.
        >
        > &gt; docker pull elasticsearch:2.3.4
        > &gt; mkdir $HOME/Docker
        > &gt; mkdir $HOME/Docker/data
        > &gt; docker run -d -p 9200:9200 -p 9300:9300 -v $HOME/Docker/data:/usr/share/elasticsearch/data --name=estest elasticsearch:2.3.4
        >


### Reference URL List

1. [Spring Data Elasticsearch](http://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/)
2. [Spring Data Elasticsearch Example](https://examples.javacodegeeks.com/enterprise-java/spring/spring-data-elasticsearch-example/)
3. [Introduction to Spring Data Elasticsearch](http://www.baeldung.com/spring-data-elasticsearch-tutorial)
4. [First Step with Spring Boot and Elasticsearch](https://dzone.com/articles/first-step-spring-boot-and)
5. [Spring boot elasticsearch (ko)](http://peyton.tk/index.php/post/792)