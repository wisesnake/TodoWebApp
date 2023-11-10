package org.zerock.jdbcex.controller;


import lombok.extern.log4j.Log4j2;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;


@WebServlet("/login")
@Log4j2
public class LoginController extends HttpServlet {



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        log.info("login get.............");

        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        log.info("login post...............");

        String mid = request.getParameter("mid");
        String mpw = request.getParameter("mpw");
        String str = mid+mpw;
        session.setAttribute("loginInfo",str);
        response.sendRedirect("/todo/list");


    }
}