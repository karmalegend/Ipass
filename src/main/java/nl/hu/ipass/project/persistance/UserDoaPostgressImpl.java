package nl.hu.ipass.project.persistance;

import nl.hu.ipass.project.persistance.DaoInterfaces.UserDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDoaPostgressImpl extends PostgresBaseDao implements UserDao {


    /*
    *
    * Get the role of a user
    *
    * */
    @Override
    public String findRoleForUser(String name, String pass){
        try(Connection con = getConnection()) {
            PreparedStatement roleFinder = con.prepareStatement("SELECT role FROM users WHERE username = (?) AND password = (?)");
            roleFinder.setString(1, name);
            roleFinder.setString(2, pass);

            ResultSet result = roleFinder.executeQuery();
            if(result.next()){
                String role = result.getString("role");
                System.out.println(result);
                System.out.println("User " + name + " with role "+ role + " logged in");
                return role;
            }
            else{
                return null;
            }
        }
        catch (SQLException e){
            System.out.println("no user found");
            System.out.println(e);
            return null;
        }
    }



    /*
    *
    * add a new user
    *
    *
    * */
    @Override
    public boolean addUser(String username, String password, String role){
        try(Connection con = getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO users(username,password,role) VALUES(?,?,?)");
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            preparedStatement.setString(3,role);

            preparedStatement.executeUpdate();
            return true;
        }
        catch (SQLException e){
            System.out.println("failed to add user");
            e.printStackTrace();
            return false;
        }
    }
}
