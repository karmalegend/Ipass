package nl.hu.ipass.project.persistance;

import nl.hu.ipass.project.persistance.DaoInterfaces.OrderDao;
import nl.hu.ipass.project.persistance.pojos.Order;
import nl.hu.ipass.project.persistance.pojos.Package;
import nl.hu.ipass.project.persistance.pojos.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDaoPostgressImpl extends PostgresBaseDao implements OrderDao {
    private Connection con = getConnection();

    @Override
    public Order getOrderbyId(int id) {
        try{
            PreparedStatement prepOrdId = con.prepareStatement("SELECT ServiceID FROM Bestellingen WHERE bestellingID = ?");
            prepOrdId.setInt(1,id);

            ResultSet res = prepOrdId.executeQuery();

            ArrayList<Service> services = new ArrayList();
            Order ord = new Order(id,services);
            ServiceDaoPostgressImpl getser = new ServiceDaoPostgressImpl();

            while (res.next()){
                int serviceID = res.getInt("ServiceID");
                Service ser = getser.getServiceByID(serviceID);
                ord.addService(ser);

            }
            return ord;

        }
        catch (SQLException e){
            System.out.println("this error comes from method 1 in orderdao");
            System.out.println(e);
            e.printStackTrace();
        }
        return null;
    }

    public Order getOrdersByPackageID(int id){
        try{
            PreparedStatement state = con.prepareStatement("SELECT * FROM Bestellingen WHERE PakketID = ?");

            state.setInt(1,id);

            ResultSet results = state.executeQuery();
            int orderID =0;

            ServiceDaoPostgressImpl addser = new ServiceDaoPostgressImpl();

            ArrayList<Service> arlist = new ArrayList<>();

            while(results.next())//noinspection duplicate
                 {
                orderID = results.getInt("BestellingID");
               int serviceID = results.getInt("ServiceID");
               Service ser = addser.getServiceByID(serviceID);
               arlist.add(ser);
            }

            Order temp = new Order(orderID,arlist);
            return temp;

        }
        catch(SQLException e){
            System.out.println("this error comes from method 2 in orderdao");
            System.out.println(e);
            e.printStackTrace();
        }
        return null;
    }
}