package nl.hu.ipass.project.persistance.DaoInterfaces;

public interface UserDao {
    String findRoleForUser(String name,String pass);
    boolean addUser(String username, String password, String role);
}
