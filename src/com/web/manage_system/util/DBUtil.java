package com.web.manage_system.util;

import java.sql.*;
import java.util.ResourceBundle;

/*
* 操作数据库的工具包
* */
public class DBUtil {
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        /*
        * 去本地加载数据库资源配置文件
        * */
        ResourceBundle bundle = ResourceBundle.getBundle("jdbc");
        String driver = bundle.getString("driver");
        String url = bundle.getString("url");
        String username = bundle.getString("username");
        String password = bundle.getString("password");
        Class.forName(driver);
        return DriverManager.getConnection(url,username,password);
    }
    public static void close(Connection conn, Statement stm, ResultSet rs){
        if (rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stm != null){
            try {
                stm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
