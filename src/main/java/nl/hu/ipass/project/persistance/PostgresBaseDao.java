package nl.hu.ipass.project.persistance;

import java.sql.*;

public class PostgresBaseDao  {
//    protected static final String USER = "omlpmnzbohximz";
//    protected static final String PASS = "335ff82bc930462afd03cb30507941d807b3f46f729ba6f00f1911826179a4dd";
//    protected static final String URL = "jdbc:postgresql://ec2-54-228-246-214.eu-west-1.compute.amazonaws.com:5432/d1clal5csmkaor";
    protected static final String URL = "jdbc:postgresql://ec2-54-228-246-214.eu-west-1.compute.amazonaws.com:5432/d1clal5csmkaor?user=omlpmnzbohximz&password=335ff82bc930462afd03cb30507941d807b3f46f729ba6f00f1911826179a4dd&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
//    postgres://omlpmnzbohximz:335ff82bc930462afd03cb30507941d807b3f46f729ba6f00f1911826179a4dd@

    protected static Connection getConnection(){
        try{
            Connection myConn = DriverManager.getConnection(URL);
            return myConn;
        }
        catch (Exception excp){
            throw new RuntimeException("Error connecting to databse", excp);
        }
    }

}
