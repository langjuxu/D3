package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * 登陆控制层
 *
 * @author qian
 * @date 2018/7/4
 */
@Controller
public class LoginController {

    // 未登录时，get请求跳转到登陆页面
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        System.err.println("-------login page------");
        return "login";
    }

    // 登陆页面登陆
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request) {
        System.err.println("------login start-----");


        System.err.println("-------login end------");
        return "login";
    }

    // 登陆成功的跳转页面
    @RequestMapping(value = {"/", "/index"})
    public String index() {
        System.err.println("....come on.....index..........");
        return "index1";
    }

    // 退出登录
    @RequestMapping(value = "/logout")
    public String logout() {
        System.err.println("-------退出登录-----");
        return "logout";
    }

    // 403：未授权
    @RequestMapping(value = "/403")
    public String unauthorizedRole() {
        System.out.println("------403:没有权限-------");
        return "403";
    }

}
