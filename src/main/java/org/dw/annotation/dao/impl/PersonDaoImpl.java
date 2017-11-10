package org.dw.annotation.dao.impl;

import org.dw.annotation.dao.PersonDao;
import org.springframework.stereotype.Service;
/**
 * Created by daiwei on 2017/11/9.
 */
@Service("personDao")
public class PersonDaoImpl implements PersonDao{
    @Override
    public void save() {
        System.out.println("执行PersonDaoImpl中的save()方法");
    }
}
