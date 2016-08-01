package com.example.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * Created by SimonKim on 8/1/16.
 */
@Document(indexName = "data", type = "mydata")
public class MyData {
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
    private Date createDate;

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
