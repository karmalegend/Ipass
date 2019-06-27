package nl.hu.ipass.project.persistance.DaoInterfaces;

import nl.hu.ipass.project.persistance.pojos.Order;
import nl.hu.ipass.project.persistance.pojos.Package;
import nl.hu.ipass.project.persistance.pojos.Service;

public interface OrderDao {
    Order getOrderbyId(int id);
    Order getOrdersByPackageID(int id);
    boolean addOrder(Package pakket, Service service,Order order);
}
