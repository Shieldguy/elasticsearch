package com.example.controller;

import com.example.domain.MyData;
import com.example.repository.MyDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SimonKim on 8/1/16.
 */
@RestController
public class MyController {

    //@Autowired
    //private EmployeeRepository repository;

    @Autowired
    private MyDataRepository repository;

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
    public @ResponseBody
    List<MyData> getMyData() {
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
