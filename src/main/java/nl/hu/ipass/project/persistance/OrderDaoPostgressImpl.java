package nl.hu.ipass.project.persistance;

import nl.hu.ipass.project.persistance.DaoInterfaces.OrderDao;
import nl.hu.ipass.project.persistance.pojos.Order;
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
            System.out.println(e);
            e.printStackTrace();
        }
        return null;
    }

    public Order getOrdersByPackageID(int PackageId){
        try{
            PreparedStatement state = con.prepareStatement("SELECT ");

        }
        catch(SQLException e){
            System.out.println(e);
            e.printStackTrace();
        }
        return null;
    }
}