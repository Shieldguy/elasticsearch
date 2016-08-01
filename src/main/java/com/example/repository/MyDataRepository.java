package com.example.repository;

import com.example.domain.MyData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by SimonKim on 8/1/16.
 */
public interface MyDataRepository extends ElasticsearchRepository<MyData, Long> {
}
