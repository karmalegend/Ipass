package nl.hu.ipass.project.persistance;

import java.sql.*;

public class PostgresBaseDao  {
    protected static final String USER = "postgres";
    protected static final String PASS = "b1b2b3b4";
    protected static final String URL = "jdbc:postgresql://localhost:5432/ipass";

    protected static Connection getConnection(){
        try{
            Connection myConn = DriverManager.getConnection(URL,USER,PASS);
            return myConn;
        }
        catch (Exception excp){
            throw new RuntimeException("Error connecting to databse", excp);
        }
    }

}
