package nl.hu.ipass.project.persistance;


import java.sql.*;

public class PostgresBaseDao  {
    protected static final String USER = "bhasjvqwkedpuz";
    protected static final String PASS = "642a9e039d6da5080958464b5f84b89b7ade69f900740999e2d6cdc2ec046dee";
    protected static final String URL = "jdbc:postgresql://ec2-54-228-246-214.eu-west-1.compute.amazonaws.com:5432/dtcgokk40hdis";

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
