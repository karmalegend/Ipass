package nl.hu.ipass.project.persistance;

import nl.hu.ipass.project.persistance.DaoInterfaces.OrderDao;
import nl.hu.ipass.project.domein.Order;
import nl.hu.ipass.project.domein.Package;
import nl.hu.ipass.project.domein.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDaoPostgressImpl extends PostgresBaseDao implements OrderDao {



    /*Get an order by ID to add it to a customer
    * parses data into a domain object*/
    @Override
    public Order getOrderbyId(int id) {
        try(Connection con = getConnection()){
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


    /*Add an order to a pacakge*/
    @Override
    public boolean addOrder(Package pakket, Service service, Order order) {
        try(Connection con = getConnection()){
            PreparedStatement preps = con.prepareStatement("INSERT INTO bestellingen(bestellingid,serviceid,pakketid) VALUES(?,?,?)");

            preps.setInt(1,order.getOrderID());
            preps.setInt(2,service.getServiceID());
            preps.setInt(3,pakket.getPackageID());

            preps.executeUpdate();
            return true;

        }
        catch (SQLException e){
            System.out.println("this error comes from method 2 in orderdao");
            System.out.println(e);
            e.printStackTrace();
            return false;
        }
    }


    /*
    *
    * add a service to an existing package
    *
    * */
    @Override
    public boolean addServiceToOrder(int orderID, int serviceID, int packageID) {
        try(Connection con = getConnection()){
            PreparedStatement addSerOrd = con.prepareStatement("INSERT INTO bestellingen(bestellingid,serviceid,pakketid) VALUES(?,?,?)");
            addSerOrd.setInt(1,orderID);
            addSerOrd.setInt(2,serviceID);
            addSerOrd.setInt(3,packageID);

            addSerOrd.executeUpdate();
            return true;

        }
        catch(SQLException e){
            System.out.println(e);
            e.printStackTrace();
            System.out.println("this error comes from method 3 in orderdao");
            return false;
        }
    }



    /*Get the orders of a package
    *
    * this is done to set default package services
    * */
    @Override
    public Order getOrdersByPackageID(int id){
        try(Connection con = getConnection()){
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
            System.out.println("this error comes from method 4 in orderdao");
            System.out.println(e);
            e.printStackTrace();
        }
        return null;
    }




    /*Delete an order
    * based on its ID
    * */

    @Override
    public boolean deleteOrderById(int orderId){
        try(Connection con = getConnection()) {
            PreparedStatement pres = con.prepareStatement("Delete FROM bestellingen where bestellingid = ?");
            pres.setInt(1, orderId);
            pres.executeUpdate();
            return true;
        }
        catch (SQLException e){
            System.out.println("this error comes from method five in orderdao");
            System.out.println(e);
            e.printStackTrace();
            return false;
        }
    }
}