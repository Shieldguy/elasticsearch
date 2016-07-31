package com.example.repository;

import com.example.domain.Employee;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * Created by wjkim on 7/31/16.
 */
//@repository
public interface EmployeeRepository extends ElasticsearchRepository<Employee, Long> {
    List<Employee> findEmployeesByAge(int age);
    List<Employee> findEmployeesByName(String name);
    List<Employee> findEmployeesBySkillsIn(List<String> skills);
}
