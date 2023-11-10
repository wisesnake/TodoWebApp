package org.zerock.jdbcex.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TodoDAO {
    public String getTime(){
        String now = null;
        try {
            Connection connection = ConnectionUtil.INSTANCE.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select now() as time");
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            now = resultSet.getString("time");
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("시간 조회시 예외 발생했음!");
        }
        return now;
    }
}
