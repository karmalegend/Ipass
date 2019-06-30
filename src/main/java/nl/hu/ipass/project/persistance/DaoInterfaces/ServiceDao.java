package nl.hu.ipass.project.persistance.DaoInterfaces;

import nl.hu.ipass.project.domein.Service;

import java.util.ArrayList;

public interface ServiceDao {
    void addService(Service service);

    Service getServiceByID(int serid);
    ArrayList<Service> getAllServices();

    void deleteServiceByID(int id);
}
