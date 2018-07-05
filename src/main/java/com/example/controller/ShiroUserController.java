package com.example.controller;

import com.example.entity.User;
import com.example.service.UserService;
import com.example.util.EncodeUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.Random;

/**
 * 用户控制层
 *
 * @author qian
 * @date 2018/7/2
 */
@Controller
public class ShiroUserController {

    @Autowired
    private UserService userService;

    // 添加用户
    //    @RequiresRoles("admin")// 添加角色
    @RequiresPermissions("user:creat")// 添加权限
    @RequestMapping(value = "/creatUser")
    public void creatUser() {
        User user = new User();
        user.setUid(3);
        user.setName("feng3");
        user.setUsername("lang3");
        String salt = EncodeUtil.encodeMD5(user.getUsername() + new Random().nextInt());
        user.setPassword(EncodeUtil.shiroSHA1Encode("111111", salt));
        user.setSalt(salt);
        user.setState(1);
        user.setCreatTime(new Date());
        userService.save(user);
    }

    // 更新用户
    @RequiresRoles("admin1")// 添加角色
//    @RequiresPermissions("user:update")// 添加权限
    @RequestMapping(value = "/updateUser")
    public void updateUser() {
        int updateUser = userService.updateUser(EncodeUtil.encodeSHA("111111" + "lang1"), new Date(), 1);
        System.err.println(updateUser);
    }

}
