package com.example;

/**
 * Created by zc on 2017/12/8.
 */

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 项目导入报错处理：重新添加jar包
 * D:\win7-x64\Java\apache-tomcat-7.0.42\lib\servlet-api.jar
 *
 * URL:http://localhost:8080/Aron_Dynamic_Web_Servlet/dologin
 *
 * Servlet implementation class ServletForAndroidClient
 */
@WebServlet("/dologin")
public class ServletForAndroidClient extends HttpServlet{
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        System.out.println("--doget--");
        doPost(request, response);
    }

    // 网页客户端传参格式
    // http://localhost:8080/Aron_Dynamic_Web_Servlet/dologin?LoginName=zc_china&LoginPassword=123456
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        System.out.println("--dopost--");

        // 线程沉睡10秒（让安卓客户端报响应超时）
        // try {
        // Thread.sleep(10000);
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // }

        String loginName = request.getParameter("LoginName");
        String loginPassword = request.getParameter("LoginPassword");
        System.out.println(loginName);
        System.out.println(loginPassword);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = null;

        try {
            out = response.getWriter();
            if (loginName.equals("zc_china") && loginPassword.equals("123456")) {
                out.print("success");
            } else {
                out.print("failed");
            }
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
