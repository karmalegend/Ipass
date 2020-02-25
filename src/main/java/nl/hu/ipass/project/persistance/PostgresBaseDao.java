package nl.hu.ipass.project.persistance;


import java.sql.*;

public class PostgresBaseDao  {
    protected static final String USER = "prchsskzaglrhj";
    protected static final String PASS = "bf7a8ef22d301e845c95b3262fbc23516d6b02aa0d113b1b88c989ba95c665f1";
    protected static final String URL = "jdbc:postgresql://ec2-54-247-189-1.eu-west-1.compute.amazonaws.com:5432/d3ht24rb2redi7";

    /*
    *
    * Return a connection object that can be
    * used by the DAO implementations
    *
    * */
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
