package org.zerock.jdbcex.controller;


import lombok.extern.log4j.Log4j2;
import org.modelmapper.internal.util.Members;
import org.zerock.jdbcex.dto.MemberDTO;
import org.zerock.jdbcex.service.MemberService;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.net.HttpCookie;
import java.util.UUID;


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


        log.info("login post...............");

        String mid = request.getParameter("mid");
        String mpw = request.getParameter("mpw");
        String auto = request.getParameter("auto");
        //login.jsp에서 auto checkbox 에서 체크했을 때 "on"이 담겨서 전송됨.

        String str = mid + mpw;

        boolean rememberMe = auto != null && auto.equals("on");
        log.info("-----------------------------");
        log.info(mid + " 님이 로그인했습니다.");
        log.info("자동로그인 bool : " + rememberMe);


        try {
            MemberDTO memberDTO = MemberService.INSTANCE.login(mid, mpw);
            //쿠키 + 세션 자동 로그인 기능
            //쿠키의 유효기간을 지정하는 경우 브라우저가 종료되더라도 보관되는 방식으로 동작하게 되므로 모바일에서는 매번 사용자가 로그인하는 수고로움을 덜어 줄 수 있다.
            if(rememberMe){
                String uuid = UUID.randomUUID().toString();
                //uuid를 랜덤으로 생성하여 부여
                //uuid란? : 네트워크상에서 고유적으로 식별되는 ID를 만들기 위한 규약
                //Universally Unique ID

                MemberService.INSTANCE.updateUuid(mid,uuid);

                Cookie rememberCookie = new Cookie("remember-me",uuid);
                rememberCookie.setMaxAge(60*60*24*7);  //쿠키의 유효기간은 1주일
                rememberCookie.setPath("/");

                response.addCookie(rememberCookie);

            }


            HttpSession session = request.getSession();
            session.setAttribute("loginInfo", memberDTO);
            response.sendRedirect("/todo/list");
        } catch (Exception e) {
            log.info("로그인 실패");
            response.sendRedirect("/login?result=error");
        }


    }
}