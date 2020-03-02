package com.web.manage_system.web.servlet;

import com.web.manage_system.domain.Page;
import com.web.manage_system.domain.User;
import com.web.manage_system.service.UserService;
import com.web.manage_system.service.implement.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

@WebServlet("/findUserByPageServlet")
public class FindUserByPageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        //1.接受请求参数currentPage和rows
        String tmp_currentPage = request.getParameter("currentPage");
        String tmp_rows = request.getParameter("rows");
        int currentPage = 1;
        int rows = 5;
        if (tmp_currentPage != null && Integer.parseInt(tmp_currentPage) > 0){
            currentPage = Integer.parseInt(tmp_currentPage);
        }
        if (tmp_rows != null && Integer.parseInt(tmp_rows) > 0){
            rows = Integer.parseInt(tmp_rows);
        }
        //1.1接受复杂查询参数
        Map<String, String[]> condition = request.getParameterMap();
        Set<String> tmp = condition.keySet();
        //2.调用service查询Page
        UserService service = new UserServiceImpl();
        Page<User> page = service.findPage(condition,currentPage,rows);
        if (page.getCurrentPage() > page.getTotalPage()) page.setCurrentPage(page.getTotalPage());
        //3.调用requset存入Page
        request.setAttribute("page",page);
        request.setAttribute("condition",condition);  //存入condition便于查询回显
        //4.转发到list.jsp
        request.getRequestDispatcher("/list.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
