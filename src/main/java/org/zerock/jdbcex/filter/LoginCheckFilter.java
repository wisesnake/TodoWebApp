package org.zerock.jdbcex.filter;

import lombok.extern.log4j.Log4j2;
import org.zerock.jdbcex.dto.MemberDTO;
import org.zerock.jdbcex.service.MemberService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@WebFilter(urlPatterns = "/todo/*")
@Log4j2
public class LoginCheckFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("로그인 피렅 ");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("login boolean assessing");


        HttpServletResponse resp = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;

        HttpSession session = req.getSession();
        // loginINfo 이름의 값이 존재하지 않으면 /login으로 리디렉션
        if(session.getAttribute("loginInfo") == null){
            Cookie cookie = findCookie(req.getCookies(),"remember-me");
            if(cookie != null){

                log.info("cookie는 존재하는 상황");
                String uuid  = cookie.getValue();

                try {
                    MemberDTO memberDTO = MemberService.INSTANCE.getByUUID(uuid);

                    log.info("쿠키의 값으로 조회한 사용자 정보: " + memberDTO );

                    session.setAttribute("loginInfo", memberDTO);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                chain.doFilter(request, response);
                return;
            }



            resp.sendRedirect("/login");
            return;
        }


        chain.doFilter(request, response);
        //Causes the next filter in the chain to be invoked,
        // or if the calling filter is the last filter in the chain, causes the resource at the end of the chain to be invoked

    }

    private Cookie findCookie(Cookie[] cookies, String name){

        if(cookies == null || cookies.length == 0){
            return null;
        }

        Optional<Cookie> result = Arrays.stream(cookies).filter(ck -> ck.getName().equals(name)).findFirst();
        //optional 클래스는, 내용이 존재하거나 null일 수도 있는 객체를 포장하는 클래스이다.
        //Optional 참조변수에 초기화한 데이터가 null을 리턴받아 초기화된다면, isPresent()메소드를 통해 false를 반환받는다
        //따라서, 해당 기능(자동로그인 필터) 에서는, 자동로그인 체크 여부 쿠키가 있는지 없는지를 검색 후, 해당 쿠키를 반환하거나
        //null을 반환하도록 하는데 쓰였다.

        return result.isPresent()?result.get():null;
    }
    @Override
    public void destroy() {
        log.info("로그인 필터 종료");
    }
}
