package nl.hu.ipass.project.persistance.DaoInterfaces;

import nl.hu.ipass.project.domein.Customer;
import nl.hu.ipass.project.domein.Package;

public interface PackageDao {
    Package getPackagebyID(int id);
    void deletePackagebyID(int id);
    void removeOrderbyId(Package pakket,int id);
    void addOrderByID(Package pakket,int id);
    Package getPackageByCustomerID(int id);
    boolean addPackage(Package pakket, Customer klant);
    public boolean editPackage(String packageName, int packagePrice, int customerID);
}
