package nl.hu.ipass.project.persistance.pojos;

import java.util.ArrayList;

public class Package {
    private int packageID;
    private String packageName;
    private int pagckagePrice;
    private ArrayList<Order> orders;

    public Package(int packageID, String packageName, int pagckagePrice, ArrayList<Order> orders) {
        this.packageID = packageID;
        this.packageName = packageName;
        this.pagckagePrice = pagckagePrice;
        this.orders = orders;
    }

    public ArrayList<Order> addOrder(Order order){
        orders.add(order);
        return orders;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public int getPackageID() {
        return packageID;
    }

    public void setPackageID(int packageID) {
        this.packageID = packageID;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getPagckagePrice() {
        return pagckagePrice;
    }

    public void setPagckagePrice(int pagckagePrice) {
        this.pagckagePrice = pagckagePrice;
    }

    @Override
    public String toString() {
        return "Package{" +
                "packageID=" + packageID +
                ", packageName='" + packageName + '\'' +
                ", pagckagePrice=" + pagckagePrice +
                ", orders=" + orders +
                '}';
    }
}
