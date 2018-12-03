package com.roger.biz.service.impl;

import com.roger.SpringBaseTestSuit;
import com.roger.biz.service.UserService;
import com.roger.core.model.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class UserServiceImplTest extends SpringBaseTestSuit {

    @Autowired(required = false)
    private UserService userService;

    @Test
    public void save() {
        User user = new User();
        user.setUserName("Jackson");
        user.setAge(38);
        user.setPhone("15498756489");
        userService.save(user);
    }
}