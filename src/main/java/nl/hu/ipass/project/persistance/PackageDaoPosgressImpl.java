package nl.hu.ipass.project.persistance;


import nl.hu.ipass.project.persistance.DaoInterfaces.PackageDao;
import nl.hu.ipass.project.domein.Customer;
import nl.hu.ipass.project.domein.Order;
import nl.hu.ipass.project.domein.Package;
import nl.hu.ipass.project.domein.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/*
*
* removed weird comment
*
* */

public class PackageDaoPosgressImpl extends PostgresBaseDao implements PackageDao {


    /*
    *
    * get a package by its ID
    *
    *
    * */

    @Override
    public Package getPackagebyID(int id) {
        try(Connection con = getConnection()){
            PreparedStatement getPackID = con.prepareStatement("SELECT * FROM Pakket WHERE PakketID = ?");
            getPackID.setInt(1,id);

            ResultSet rs = getPackID.executeQuery();

            OrderDaoPostgressImpl orderdao = new OrderDaoPostgressImpl();

            if(rs.next()) //noinspection Duplicates
                 {
              int idp =  rs.getInt("PakketID");
              String pakketnaam = rs.getString("Pakketnaam");
              int Pakketprijs =  rs.getInt("Pakketprijs");
              ArrayList<Order> orders = new ArrayList<>();

              orders.add(orderdao.getOrdersByPackageID(idp));


              Package pack = new Package(idp,pakketnaam,Pakketprijs,orders);
              return pack;
            }

        }
        catch(SQLException e){
            System.out.println("this error comes from method 1 in packagedao");
            System.out.println(e);
            e.printStackTrace();
        }
        return null;
    }


    /*delete a package */

    @Override
    public void deletePackagebyID(int id) {
        try(Connection con = getConnection()){
            PreparedStatement preps = con.prepareStatement("DELETE FROM Pakket" +
                    " WHERE PakketID = ?");
            preps.setInt(1,id);
            preps.executeQuery();
        }
        catch(SQLException e){
            System.out.println("this error comes from method 2 in packagedao");
            System.out.println(e);
            e.printStackTrace();
        }

    }


    /*remove an order when customer gets deleted*/
    @Override
    public void removeOrderbyId(Package pakket,int id) {
        try(Connection con = getConnection()){
            PreparedStatement preps = con.prepareStatement("DELETE FROM bestellingen" +
                    " WHERE BestellingID = ?");
            preps.executeQuery();

        }
        catch(SQLException e){
            System.out.println("this error comes from method 3 in packagedao");
            System.out.println(e);
            e.printStackTrace();
        }

    }

    /*
    *
    * add an order to a package based on id
    *
    * */
    @Override
    public void addOrderByID(Package pakket,int id) {
        try(Connection con = getConnection()){
            PreparedStatement preps = con.prepareStatement("SELECT * FROM Bestellingen" +
                    " WHERE BestellingID = ?");
            int orderID = 0;

            preps.setInt(1,id);

            ResultSet res = preps.executeQuery();

            ArrayList<Service> services = new ArrayList<>();

            ServiceDaoPostgressImpl serDao = new ServiceDaoPostgressImpl();

            while (res.next())//noinspection Duplicates
            {
                orderID = res.getInt("BestellingID");
                int serviceID = res.getInt("ServiceID");

                Service temp = serDao.getServiceByID(serviceID);

                services.add(temp);

            }
            Order order = new Order(orderID,services);
            pakket.addOrder(order);


        }
        catch(SQLException e){
            System.out.println("this error comes from method 4 in packagedao");
            System.out.println(e);
            e.printStackTrace();
        }

    }



    /*Get a package by customerID
    *
    * to render all customers*/

    @Override
    public Package getPackageByCustomerID(int id){

        try(Connection con = getConnection()){
            PreparedStatement preps = con.prepareStatement("SELECT * FROM pakket WHERE Klantnummer = ?");
            preps.setInt(1,id);
            ResultSet item = preps.executeQuery();

            OrderDaoPostgressImpl orderdao = new OrderDaoPostgressImpl();


            if(item.next())//noinspection Duplicates
                 {
                int idp = item.getInt("PakketID");
                int customer = item.getInt("Klantnummer");
                String name = item.getString("Pakketnaam");
                int price = item.getInt("Pakketprijs");

                ArrayList<Order> orders = new ArrayList<>();

                orders.add(orderdao.getOrdersByPackageID(id));

                return new Package(idp,name,price,orders);
            }
        }
        catch(SQLException e){
            System.out.println("this error comes from method 5 in packagedao");
            System.out.println(e);
            e.printStackTrace();
        }
        return null;
    }


//    TODO : addPackage function


    /*
    *
    *
    * add package functions
    *
    * adds a package to a customer
    * goes via domainmodel
    *
    * */

    @Override
    public boolean addPackage(Package pakket, Customer klant) {
        try(Connection con = getConnection()){
            PreparedStatement preps = con.prepareStatement("INSERT INTO pakket(pakketid,klantnummer,pakketnaam,pakketprijs) VALUES(?,?,?,?)");
            preps.setInt(1,pakket.getPackageID());
            preps.setInt(2,klant.getCustomerID());
            preps.setString(3,pakket.getPackageName());
            preps.setInt(4,pakket.getPagckagePrice());

            preps.executeUpdate();
            return true;

        }
        catch(SQLException e){
            System.out.println("This error comes from method 6 in packagedao");
            System.out.println(e);
            e.printStackTrace();
            return false;
        }
    }


    /*Edit a package and its details*/
    @Override
    public boolean editPackage(String packageName, int packagePrice, int customerID){
        try(Connection con = getConnection()){
            PreparedStatement preps = con.prepareStatement("UPDATE pakket SET pakketnaam = ?, pakketprijs = ? WHERE klantnummer = ?");
            preps.setString(1,packageName);
            preps.setInt(2,packagePrice);
            preps.setInt(3,customerID);

            preps.executeUpdate();
            return true;
        }
        catch (SQLException e){
            System.out.println("this error comes from method 7 in packagedao");
            System.out.println(e);
            e.printStackTrace();
            return false;
        }
    }
}
