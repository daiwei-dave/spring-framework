package com.roger.biz.service.impl;

import com.roger.biz.dao.UserDao;
import com.roger.biz.service.UserService;
import com.roger.core.annotaion.CustomTransactional;
import com.roger.core.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired(required = false)
    private UserDao userDao;

    @CustomTransactional
    @Override
    public void save(User user) {
        userDao.insert(user);
        //利用最简单的构造异常来验证事务的回滚操作
        //通过回滚操作来和事务提交作对比
        //int i = 1 / 0 ;
    }
}
