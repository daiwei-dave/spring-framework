package org.dw.annotation.dao.impl;

import org.dw.annotation.dao.PersonDao;

/**
 * Created by daiwei on 2017/11/9.
 */
public class PersonDaoImpl implements PersonDao{
    @Override
    public void save() {
        System.out.println("执行PersonDaoImpl中的save()方法");
    }
}
