package com.web.manage_system.web.servlet;

import com.web.manage_system.domain.User;
import com.web.manage_system.service.UserService;
import com.web.manage_system.service.implement.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/findUserServlet")
public class FindUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取id
        String id = request.getParameter("id");
        //2.调用service查询
        UserService service = new UserServiceImpl();
        User user = service.findId(id);
        //3.将User存入request
        request.setAttribute("user",user);
        //4.转发到update.jsp
        request.getRequestDispatcher("/update.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
