package nl.hu.ipass.project.persistance.DaoInterfaces;

import nl.hu.ipass.project.domein.Order;
import nl.hu.ipass.project.domein.Package;
import nl.hu.ipass.project.domein.Service;

public interface OrderDao {
    Order getOrderbyId(int id);
    Order getOrdersByPackageID(int id);
    boolean addOrder(Package pakket, Service service,Order order);
    boolean addServiceToOrder(int orderID,int serviceID,int packageID);
    boolean deleteOrderById(int orderId);
}
