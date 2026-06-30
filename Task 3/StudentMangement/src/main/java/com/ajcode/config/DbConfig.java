package com.ajcode.config;

import java.sql.*;

public class DbConfig {
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://localhost:5432/student_management";
    private static final String USER = "postgres";
    private static final String PASS = "Abhay@1234";

    public static Connection getConnection()
    {
        try{
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(URL,USER,PASS);
            return con;
        }catch (ClassNotFoundException | SQLException ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    public static void close(Object...objects)
    {
        try {
            for(Object obj : objects)
            {
                if(obj instanceof Statement)
                {
                    Statement stmt = (Statement)obj;
                    stmt.close();
                }else if (obj instanceof ResultSet)
                {
                    ResultSet rs = (ResultSet)obj;
                    rs.close();
                }
            }
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

}
