package com.web.manage_system.dao;

/*
* 用户操作的dao
*/

import com.web.manage_system.domain.Manager;
import com.web.manage_system.domain.User;

import java.util.List;
import java.util.Map;

public interface UserDao {
    /*
    * 向数据库中查询所有User的集合
    * */
    public List<User> findAll();
    /*
    * 管理员的登陆查询
    * */
    public Manager login(Manager manager);
    /*
    * 向数据库中添加用户信息
    * */
    public void addUser(User user);
    /*
    * 删除数据库中指定的用户信息
    * */
    public void delUser(String id);
    /*
    * 根据id向数据库查询用户
    * */
    public User findId(String id);
    /*
    * 去数据库中更新用户信息
    * */
    public void updateUser(User user);
    /*
    * 查询满足指定条件的用户数量
    * */
    public int findCount(Map<String, String[]> condition);
    /*
    * 查询满足指定条件的用户数量并分页显示
    * */
    public List<User> findUserList(int start, int rows, Map<String, String[]> condition);
}
