package nl.hu.ipass.project.persistance.DaoInterfaces;

import nl.hu.ipass.project.persistance.pojos.Customer;
import nl.hu.ipass.project.persistance.pojos.Package;

public interface PackageDao {
    Package getPackagebyID(int id);
    void deletePackagebyID(int id);
    void removeOrderbyId(Package pakket,int id);
    void addOrderByID(Package pakket,int id);
    Package getPackageByCustomerID(int id);
    boolean addPackage(Package pakket, Customer klant);
}
