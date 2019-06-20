package nl.hu.ipass.project.persistance.pojos;

import java.util.ArrayList;

public class Order {
    private int orderID;
    private ArrayList<Service> services;

    public Order(int orderID, ArrayList<Service> services) {
        this.orderID = orderID;
        this.services = services;
    }

    public ArrayList<Service> getServices() {
        return services;
    }

    public void setServices(ArrayList<Service> services) {
        this.services = services;
    }

    public ArrayList<Service> addService(Service dienst){
        services.add(dienst);
        return services;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }


}
