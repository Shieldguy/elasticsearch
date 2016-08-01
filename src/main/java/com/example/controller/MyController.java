package com.example.controller;

import com.example.domain.MyData;
import com.example.repository.MyDataRepository;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

/**
 * Created by SimonKim on 8/1/16.
 */
@RestController
public class MyController {

    @Autowired
    private MyDataRepository repository;

    @Autowired
    private ElasticsearchTemplate template;

    public MyController() {
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public @ResponseBody List<MyData> getMyData() {
        List<MyData> list = new ArrayList<>();
        repository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @RequestMapping(value = "/template", method = RequestMethod.GET)
    public @ResponseBody List<MyData> getTemplateMyData() {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchAllQuery())
                .withIndices("data")
                .withTypes("mydata")
                .build();
        List<MyData> list = template.queryForList(searchQuery, MyData.class);
        return list;
    }

    /*
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public @ResponseBody HttpEntity<PagedResources<MyData>> getMyData(@RequestParam("name") String name, Pageable pageable, PagedResourcesAssembler assembler) {
        Page<MyData> pages = repository.findByName(name);
        return new ResponseEntity<>(assembler.toResource(pages), HttpStatus.OK);
    }
    */

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody MyData getMyData(@PathVariable("id") String id) {
        return repository.findOne(id);
    }

    @RequestMapping(value = "/template/{id}", method = RequestMethod.GET)
    public @ResponseBody MyData getTemplateMyData(@PathVariable("id") String id) {
        GetQuery getQuery = new GetQuery();
        getQuery.setId(id);
        MyData data = template.queryForObject(getQuery, MyData.class);
        return data;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public @ResponseBody MyData addMyData(@RequestBody MyData data) {
        if(data.getCreateDate() == null)
            data.setCreateDate(new Date(System.currentTimeMillis()));
        return repository.save(data);
    }

    @RequestMapping(value = "/template", method = RequestMethod.POST)
    public @ResponseBody MyData addTempateMyData(@RequestBody MyData data) {
        if(data.getCreateDate() == null)
            data.setCreateDate(new Date(System.currentTimeMillis()));

        IndexQuery indexQuery = new IndexQuery();
        indexQuery.setObject(data);
        String index = template.index(indexQuery);
        data.setId(index);

        return repository.save(data);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public @ResponseBody MyData updateMyData(@PathVariable("id") String id, @RequestBody MyData data) {
        if(id.equals(data.getId()))
            return repository.save(data);
        else
            return null;
    }

    @RequestMapping(value = "/template/{id}", method = RequestMethod.PUT)
    public @ResponseBody Object updateTemplateMyData(@PathVariable("id") String id, @RequestBody MyData data) {
        if(id.equals(data.getId())) {
            IndexRequest indexRequest = new IndexRequest();
            indexRequest.source("name", data.getName());
            indexRequest.source("email", data.getEmail());

            UpdateQuery updateQuery = new UpdateQueryBuilder().withId(id)
                    .withClass(MyData.class).withIndexRequest(indexRequest).build();
            UpdateResponse update = template.update(updateQuery);
            return update;
        } else
            return null;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public @ResponseBody Object deleteMyData(@PathVariable("id") String id) {
        repository.delete(id);
        return ResponseEntity.ok();
    }

    @RequestMapping(value = "/template/{id}", method = RequestMethod.DELETE)
    public @ResponseBody Object deleteTemplateMyData(@PathVariable("id") String id) {
        String delete = template.delete(MyData.class, id);
        return ResponseEntity.ok(delete);
    }

}
