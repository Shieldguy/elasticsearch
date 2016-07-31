package com.example.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

/**
 * Created by wjkim on 7/31/16.
 */
@Document(indexName = "resource", type = "employees", shards = 1, replicas = 0)
public class Employee {
    @Id
    private Long id;
    private String name;
    private Integer age;

    @Field( type = FieldType.Nested)
    private List<Skill> skills;

    public Employee(){}

    public Employee(Long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public String toString() {
        return "Employee [(" + getId() + ", " + getName() + ", " + age + "), skills: " + getSkills() + "]";
    }
}
