package nl.hu.ipass.project.domein;

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

    public void addService(Service dienst){
        services.add(dienst);
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID=" + orderID +
                ", services=" + services +
                '}';
    }
}
