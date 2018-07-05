package com.example.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author qian
 * @date 2018/7/2
 */
//@Data
@Entity
@Setter
@Getter
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue
    private Integer uid;

    @Column(unique = true)
    private String username;

    private String name;

    private String password;

    // 加密密码的盐
    private String salt;

    // 用户状态,0:创建未认证（比如没有激活，没有输入验证码等等）--等待验证的用户 , 1:正常状态,2：用户被锁定.
    private int state;

    // 立即从数据库中进行加载数据;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "UserRole", joinColumns = {@JoinColumn(name = "uid")}, inverseJoinColumns = {@JoinColumn(name = "roleId")})
    // 一个用户具有多个角色
    private List<Role> roleList;

    private Date creatTime;
}
