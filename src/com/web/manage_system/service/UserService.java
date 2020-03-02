package com.web.manage_system.service;

import com.web.manage_system.domain.Manager;
import com.web.manage_system.domain.Page;
import com.web.manage_system.domain.User;

import java.util.List;
import java.util.Map;

/*
* 用户管理的业务接口
* */
public interface UserService {
    /*
    * 查询所有用户信息
    * */
    public List<User> findAll();
    /*
    * 查询管理员信息
    * */
    public Manager login(Manager manager);
    /*
    * 添加用户信息
    * */
    public void addUser(User user);
    /*
    *删除用户信息
    * */
    public void delUser(String id);
    /*
    * 通过用户id查找用户信息
    * */
    public User findId(String id);
    /*
    * 更新用户信息
    * */
    public void updateUser(User user);
    /*
    * 分页条件查询
    * */
    public Page<User> findPage(Map<String,String[]> condition, int currentPage, int rows);
}
