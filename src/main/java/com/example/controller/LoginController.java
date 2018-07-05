package com.example.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * 登陆控制层
 *
 * @author qian
 * @date 2018/7/4
 */
@Controller
public class LoginController {

    private Logger log = LoggerFactory.getLogger(LoginController.class);

    // 未登录时，get请求跳转到登陆页面
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        System.err.println("-------login page------");
        return "login";
    }

    // 登陆页面登陆
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, @RequestParam("username") String username, @RequestParam("password") String password) {
        System.err.println("------login start-----");
        //添加用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
        usernamePasswordToken.setRememberMe(true);
        //进行验证，这里可以捕获异常，然后返回对应信息void
        String msg = "";
        try {
            //7.传入上一步骤创建的token对象，登录，即进行身份验证操作。
            subject.login(usernamePasswordToken);
            System.err.println("-------login end------");
            return "index1";
        } catch (UnknownAccountException uae) {
            msg = "用户名未知...";
            log.info("用户不存在");
        } catch (IncorrectCredentialsException ice) {
            msg = "密码不正确 ...";
            log.info("密码不正确");
            request.setAttribute("msg", "密码不正确");
        } catch (LockedAccountException lae) {
            msg = "用户被禁用...";
            log.info("用户被禁用");
        } catch (ExcessiveAttemptsException eae) {
            msg = "请求次数过多，用户被锁定";
            log.info("请求次数过多，用户被锁定");
        } catch (AuthenticationException ae) {
            msg = "未知错误，无法完成登录";
            log.info("未知错误，无法完成登录");
        }
        request.setAttribute("msg", msg);
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
