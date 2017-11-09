package org.dw.annotation.service.impl;

import org.dw.annotation.MyResource;
import org.dw.annotation.dao.PersonDao;
import org.dw.annotation.service.PersonService;

/**
 * Created by daiwei on 2017/11/9.
 */
public class PersonServiceImpl implements PersonService {
    @MyResource(name="personDao")
    private PersonDao personDao;
    private String name;
    private Integer id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public void save() {
        System.out.println("id: " + id + ", name: " + name);
        personDao.save();
    }
}
