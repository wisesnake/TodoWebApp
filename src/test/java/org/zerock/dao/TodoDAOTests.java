package org.zerock.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.zerock.jdbcex.dao.TodoDAO;

public class TodoDAOTests {
    private TodoDAO todoDAO;

    @BeforeEach// 현재 테스트 클래스의 각 @Test메서드 전에 주석이 달린 메서드가 실행되어야 함을 알리는데 사용
    public void ready(){
        todoDAO = new TodoDAO();
    }

    @Test
    public void testTime() throws Exception{
        System.out.println("현재 시간 테스트 결과 : " + todoDAO.getTime());
    }
}
