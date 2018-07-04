package com.example.dao;

import com.example.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;

/**
 * 用户数据层
 *
 * @author qian
 * @date 2018/7/2
 */
public interface UserDao extends CrudRepository<User, Long> {
    /**
     * 通过username查找用户信息;
     */
    User findByUsername(String username);

    /**
     * 根据用户id更新用户信息
     *
     * @param password
     * @param creatTime
     * @param uid
     * @return
     */
    @Modifying
    @Query("update User u set u.password=:password, u.creatTime=:creat_time where u.uid=:uid")
    int updateUser(@Param("password") String password, @Param("creat_time") Date creatTime, @Param("uid") int uid);

}
