package com.roger.biz.dao.impl;

import com.roger.SpringBaseTestSuit;
import com.roger.biz.dao.UserDao;
import com.roger.core.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

import static org.junit.Assert.*;

public class UserDaoImplTest extends SpringBaseTestSuit {

    @Autowired(required = false)
    private UserDao userDao;

    @Test
    public void testInsert() {
        User user = new User();
        user.setUserName("Jackson");
        user.setAge(38);
        user.setPhone("15498756489");
        int count = userDao.insert(user);
        Assert.assertTrue(count == 1);
    }
}