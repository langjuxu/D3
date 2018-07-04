package com.example.service.impl;

import com.example.dao.UserDao;
import com.example.entity.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 用户业务层接口实现
 *
 * @author qian
 * @date 2018/7/2
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public void save(User user) {
        // save方法，CrudRepository的方法
        userDao.save(user);
    }

    @Override
    // 修改时不加事务会报错
    @Transactional
    public int updateUser(String password, Date creatTime, int uid) {
        return userDao.updateUser(password, creatTime, uid);
    }
}
