package com.web.manage_system.web.servlet;

import com.web.manage_system.service.UserService;
import com.web.manage_system.service.implement.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/delUserServlet")
public class DelUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取id值
        String id = request.getParameter("id");
        //2.调用service删除
        UserService service = new UserServiceImpl();
        service.delUser(id);
        //3.跳转回userListServlet
        response.sendRedirect(request.getContextPath() + "/findUserByPageServlet?currentPage=1&rows=5");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
