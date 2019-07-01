package nl.hu.ipass.project.persistance;

import nl.hu.ipass.project.persistance.DaoInterfaces.UserDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDoaPostgressImpl extends PostgresBaseDao implements UserDao {
    private Connection con = getConnection();

    @Override
    public String findRoleForUser(String name, String pass){
        try(PreparedStatement roleFinder = con.prepareStatement("SELECT role FROM users WHERE username = (?) AND password = (?)")) {
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
}
