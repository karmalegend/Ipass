package nl.hu.ipass.project.persistance.DaoInterfaces;

public interface UserDao {
    String findRoleForUser(String name,String pass);
}
