package nl.hu.ipass.project.persistance.DaoInterfaces;

import nl.hu.ipass.project.persistance.pojos.Order;

public interface OrderDao {
    Order getOrderbyId(int id);
}
