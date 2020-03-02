package com.web.manage_system.web.servlet;

import com.web.manage_system.domain.Manager;
import com.web.manage_system.service.UserService;
import com.web.manage_system.service.implement.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置编码
        request.setCharacterEncoding("utf-8");
        //2.获取数据
        String verifycode = request.getParameter("verifycode");
        //3.验证码校验
        HttpSession session = request.getSession();
        String checkcode_server = (String)session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");  //确保验证码一次性
        if (!checkcode_server.equalsIgnoreCase(verifycode)){
            //验证码不正确，提示信息，跳转页面
            request.setAttribute("login_msg","验证码错误");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }
        //4.封装到Manager

        Map<String, String[]> map = request.getParameterMap();
        Manager manager = new Manager();
        try {
            BeanUtils.populate(manager,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //5.查询数据库
        UserService service = new UserServiceImpl();
        Manager loginManager = service.login(manager);
        //6.判断是否登陆成功并转发到相应页面
        if (loginManager != null){
            //登陆成功
            session.setAttribute("loginManager",loginManager);
            response.sendRedirect(request.getContextPath() + "/index.jsp");

        }else{
            //登陆失败
            request.setAttribute("login_msg","用户名或登陆密码错误");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
