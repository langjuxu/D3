package com.example.config;

import com.example.entity.Permission;
import com.example.entity.Role;
import com.example.entity.User;
import com.example.service.UserService;
import com.example.util.EncodeUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * )验证，以及权限的添加
 *
 * @author qian
 * @date 2018/7/4
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    // 角色和对应权限添加、校验
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.err.println("-----doGetAuthorizationInfo校验-----");
        // 获取登陆用户名
        String username = (String) principalCollection.getPrimaryPrincipal();
        // 查询用户
        User user = userService.findByUsername(username);
        if (user == null) {
            System.err.println("用户不存在！！！");
            return null;
        }
        // 添加角色和权限
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        for (Role role : user.getRoleList()) {
            // 添加角色
            System.err.println("-----添加角色------");
            authorizationInfo.addRole(role.getRole());
            for (Permission permission : role.getPermissions()) {
                // 添加权限
                System.err.println("-----添加权限------");
                authorizationInfo.addStringPermission(permission.getPermission());
            }
        }
        return authorizationInfo;
    }

    // 用户认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.err.println("-----doGetAuthenticationInfo认证-----");
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        // 获取用户信息
//        String username = authenticationToken.getPrincipal().toString();
        String username = usernamePasswordToken.getUsername();
        User user = userService.findByUsername(username);
        if (user == null) {
            System.err.println("用户不存在！！！");
            return null;
        }
        SimpleAuthenticationInfo authorizationInfo = new SimpleAuthenticationInfo(
                username,// 用户名
                user.getPassword(),// 密码
                getName());// realm name
        return authorizationInfo;
    }
}
