package nl.hu.ipass.project.persistance.DaoInterfaces;

import nl.hu.ipass.project.persistance.pojos.Service;

public interface ServiceDao {
    void addService(Service service);

    Service getServiceByID(int serid);

    void deleteServiceByID(int id);
}
