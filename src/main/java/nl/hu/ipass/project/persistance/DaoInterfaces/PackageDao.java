package nl.hu.ipass.project.persistance.DaoInterfaces;

public interface PackageDao {
    Package getPackagebyID(int id);
    void deletePackagebyID(int id);
    void removeOrderbyId(int id);
    void addOrderByID(int id);
    Package getPackageByCustomerID(int id);
}
