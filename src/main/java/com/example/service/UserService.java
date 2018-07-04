package com.example.service;


import com.example.entity.User;

import java.util.Date;

/**
 * 用户业务层接口
 *
 * @author qian
 * @date 2018/7/2
 */

public interface UserService {

    /**
     * 保存用户
     *
     * @param user
     */
    void save(User user);

    /**
     * 根据用户id更新用户信息
     *
     * @param password
     * @param creatTime
     * @param uid
     * @return
     */
    int updateUser(String password, Date creatTime, int uid);

    /**
     * 通过username查找用户信息;
     *
     * @param username
     */
    User findByUsername(String username);
}
