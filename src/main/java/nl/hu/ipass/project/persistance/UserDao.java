package nl.hu.ipass.project.persistance;

public interface UserDao {
    String findRoleForUser(String name,String pass);
}
