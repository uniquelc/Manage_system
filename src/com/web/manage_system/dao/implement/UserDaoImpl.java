package com.web.manage_system.dao.implement;

import com.web.manage_system.dao.UserDao;
import com.web.manage_system.domain.Manager;
import com.web.manage_system.domain.User;
import com.web.manage_system.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UserDaoImpl implements UserDao {
    Connection conn;
    PreparedStatement stm;
    ResultSet rs;
    public List<User> findAll() {
        /*
        * 使用JDBC操作数据库
        * */
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from manageinfo";
            stm = conn.prepareStatement(sql);
            rs = stm.executeQuery();
            List<User> list = new ArrayList<User>();
            while (rs.next()){
                User user = new User();
                user.setId(rs.getInt(1));
                user.setName(rs.getString(2));
                user.setGender(rs.getString(3));
                user.setAge(rs.getInt(4));
                user.setAddress(rs.getString(5));
                user.setEmail(rs.getString(6));
                user.setWechat(rs.getString(7));
                list.add(user);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(conn,stm,rs);
        }
        return null;
    }
    public Manager login(Manager manager){
        try {
            conn = DBUtil.getConnection();
            //编写sql语句
            String sql = "select * from manager where username=? and password=?";
            stm = conn.prepareStatement(sql);
            stm.setString(1,manager.getUsername());
            stm.setString(2,manager.getPassword());
            rs = stm.executeQuery();
            Manager tmp_manager = new Manager();
            if (rs.next() && rs.getInt(1) != 0){
                tmp_manager.setId(rs.getInt(1));
                tmp_manager.setUsername(rs.getString(2));
                tmp_manager.setPassword(rs.getString(3));
                return tmp_manager;
            }else{
                return null;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, stm, rs);
        }
        return null;
    }

    public void addUser(User user) {
        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO manageinfo (name, gender, age, address, email, wechat) VALUES (?,?,?,?,?,?)";
            stm = conn.prepareStatement(sql);
            stm.setString(1,user.getName());
            stm.setString(2,user.getGender());
            stm.setInt(3,user.getAge());
            stm.setString(4,user.getAddress());
            stm.setString(5,user.getEmail());
            stm.setString(6,user.getWechat());
            int count = stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(conn,stm,rs);
        }
    }

    public void delUser(String id) {
        try {
            conn = DBUtil.getConnection();
            String sql = "delete from manageinfo where id=?";
            stm = conn.prepareStatement(sql);
            stm.setString(1,id);
            int count = stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(conn,stm,rs);
        }
    }

    public User findId(String id) {
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from manageinfo where id=?";
            stm = conn.prepareStatement(sql);
            stm.setString(1,id);
            rs = stm.executeQuery();
            User user = new User();
            while (rs.next()){
                user.setId(rs.getInt(1));
                user.setName(rs.getString(2));
                user.setGender(rs.getString(3));
                user.setAge(rs.getInt(4));
                user.setAddress(rs.getString(5));
                user.setEmail(rs.getString(6));
                user.setWechat(rs.getString(7));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(conn,stm,rs);
        }
        return null;
    }

    public void updateUser(User user) {
        try {
            conn = DBUtil.getConnection();
            String sql = "update manageinfo set name=?,gender=?,age=?,address=?,email=?,wechat=? where id=?";
            stm = conn.prepareStatement(sql);
            stm.setString(1,user.getName());
            stm.setString(2,user.getGender());
            stm.setInt(3,user.getAge());
            stm.setString(4,user.getAddress());
            stm.setString(5,user.getEmail());
            stm.setString(6,user.getWechat());
            stm.setInt(7,user.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(conn,stm,rs);
        }
    }

    public int findCount(Map<String, String[]> condition) {
        int count = 0;
        try {
            conn = DBUtil.getConnection();
            //1.定义sql模板
            String sql = "select count(*) from manageinfo where 1=1 ";
            StringBuilder sb = new StringBuilder(sql);
            //2.遍历map
            Set<String> keySet = condition.keySet();
            //3.取一个集合储存记录key
            List<String> list = new ArrayList<>();
            for (String key : keySet){
                if ("currentPage".equals(key) || "rows".equals(key)){
                    continue;
                }
                String value = condition.get(key)[0];
                if (value != null && !"".equals(value)){
                    sb.append(" and " + key + " like ? ");
                    list.add(value);
                }
            }
            String ss = sb.toString();
            stm = conn.prepareStatement(ss);
            for (int i = 0; i < list.size(); i++){
                String tmp = "%" + list.get(i) + "%";
                stm.setString(i + 1,tmp);
            }
            rs = stm.executeQuery();
            while (rs.next()){
                count = rs.getInt(1);
            }
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(conn,stm,rs);
        }
        return count;
    }

    public List<User> findUserList(int start, int rows, Map<String, String[]> condition) {
        try {
            conn = DBUtil.getConnection();
            //1.定义sql模板
            String sql = "select * from manageinfo where 1=1 ";
            StringBuilder sb = new StringBuilder(sql);
            //2.遍历map
            Set<String> keySet = condition.keySet();
            //3.取一个集合储存记录key
            List<Object> tmp_list = new ArrayList<>();
            for (String key : keySet){
                if ("currentPage".equals(key) || "rows".equals(key)){
                    continue;
                }
                String value = condition.get(key)[0];
                if (value != null && !"".equals(value)){
                    sb.append(" and " + key + " like ? ");
                    tmp_list.add(value);
                }
            }
            //4.进行分页
            sb.append(" limit ?,?");
            tmp_list.add(start);
            tmp_list.add(rows);
            String ss = sb.toString();
            stm = conn.prepareStatement(ss);
            //5.通过遍历tmp_list向sql语句中注入值
            for (int i = 0; i < tmp_list.size(); i++) {
                if (tmp_list.get(i) instanceof String){
                    String tmp = "%" + tmp_list.get(i) + "%";
                    stm.setString(i + 1, tmp);
                }else{
                    stm.setInt(i + 1, (int)tmp_list.get(i));
                }
            }
            rs = stm.executeQuery();
            List<User> list = new ArrayList<User>();
            while (rs.next()){
                User user = new User();
                user.setId(rs.getInt(1));
                user.setName(rs.getString(2));
                user.setGender(rs.getString(3));
                user.setAge(rs.getInt(4));
                user.setAddress(rs.getString(5));
                user.setEmail(rs.getString(6));
                user.setWechat(rs.getString(7));
                list.add(user);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(conn,stm,rs);
        }
        return null;
    }
}
