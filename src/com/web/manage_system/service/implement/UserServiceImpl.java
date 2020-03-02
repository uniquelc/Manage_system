package com.web.manage_system.service.implement;

import com.web.manage_system.dao.UserDao;
import com.web.manage_system.dao.implement.UserDaoImpl;
import com.web.manage_system.domain.Manager;
import com.web.manage_system.domain.Page;
import com.web.manage_system.domain.User;
import com.web.manage_system.service.UserService;

import javax.print.DocFlavor;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private UserDao dao = new UserDaoImpl();

    @Override
    public List<User> findAll() {
        /*
         * 调用dao完成查询
         * */
        return dao.findAll();
    }

    public Manager login(Manager manager) {
        return dao.login(manager);
    }

    @Override
    public void addUser(User user) {
        dao.addUser(user);
    }

    @Override
    public void delUser(String id) {
        dao.delUser(id);
    }

    @Override
    public User findId(String id) {
        return dao.findId(id);
    }

    @Override
    public void updateUser(User user) {
        dao.updateUser(user);
    }

    @Override
    public Page<User> findPage(Map<String,String[]> condition, int currentPage, int rows) {
        if (currentPage <= 0) currentPage = 1;
        //1.创建空Page对象
        Page<User> page = new Page<User>();
        //2.设置当前页面属性和rows属性
        page.setRows(rows);
        page.setCurrentPage(currentPage);
        //3.调用dao查询总记录数
        int totalCount = dao.findCount(condition);
        page.setTotalCount(totalCount);
        if (totalCount == 0) return page;
        //4.计算起始查询数
        int start = (currentPage - 1) * rows;
        //5.调用dao查询list
        List<User> list = dao.findUserList(start, rows, condition);
        page.setList(list);
        //6.计算总页数
        int totolPage = totalCount % rows == 0 ? totalCount / rows : (totalCount / rows) + 1;
        page.setTotalPage(totolPage);
        return page;
    }
}

