package nl.hu.ipass.project.persistance;

import nl.hu.ipass.project.persistance.DaoInterfaces.CustomerDao;
import nl.hu.ipass.project.persistance.pojos.Customer;
import nl.hu.ipass.project.persistance.pojos.Package;

import java.sql.*;
import java.util.ArrayList;

public class CustomerDaoPosgressImpl extends PostgresBaseDao implements CustomerDao{
    private Connection con = getConnection();



    /*
    *
    * This function returns a list of all customers
    * Self explanatory
    * This is used to render a list of all customers on the front-end
    *
    *
    * TODO:
    *
    *
    * test function and add it to
    * @GET
    *
    * @RolesAllowed ADMIN !!!!
    *
    *
    * */

    @Override
    public ArrayList<Customer> getAllCustomers() {
        try {
            ArrayList<Customer> allCustomers = new ArrayList<>();

            Statement getCust = con.createStatement();

            ResultSet customers = getCust.executeQuery("select * from klant ORDER BY klantnummer ASC");

            PackageDaoPosgressImpl packagedao = new PackageDaoPosgressImpl();


            while (customers.next()) {
                int id = customers.getInt("Klantnummer");
                String name = customers.getString("Bedrijfsnaam");
                int kvknum = customers.getInt("KvKnummer");
                String email = customers.getString("Emailadress");
                int phNum = customers.getInt("TelefoonNummer");

                Package pakket = packagedao.getPackageByCustomerID(id);

                Customer tempCust = new Customer(id, name, kvknum, email, phNum,pakket);
                allCustomers.add(tempCust);
            }
            return allCustomers;
        }
        catch (SQLException e){
            System.out.println("this error comes from method 1 in customerdao");
            e.printStackTrace();
            System.out.println(e);
        }
        return null;
    }



    /*
    *
    * This function takes a customer as argument upon which it updates all the values of that given customer
    * in the database F.E both kvknum and email changed so you change all of those based on custNum matching
    *
    * TODO:
    *
    * Test given function
    *
    * */
    @Override
    public void updateCustomer(Customer customer) {
        try{
           PreparedStatement prepS =
                   con.prepareStatement("UPDATE Klant" +
                    "SET Bedrijfsnaam = ?, KvKnummer = ?,Emailadress = ?,TelefoonNummer = ?" +
                    "WHERE Klantnummer = ?");

           prepS.setString(1,customer.getCompanyname());
           prepS.setInt(2,customer.getKvkNumber());
           prepS.setString(3,customer.getEmailadress());
           prepS.setInt(4,customer.getPhonenumber());
           prepS.setInt(5,customer.getCustomerID());


           prepS.executeQuery();

        }
        catch (SQLException e){
            System.out.println("this error comes from method 2 in customerdao");
            e.printStackTrace();
            System.out.println(e);
        }
    }


    /*
    * This is a function that deletes a customer from the database based on it's customerID
    * This function also deletes every reference.
    *
    * TODO:
    *
    * Have added on delete cascade for now. Not yet tested.
    *
    * check query need to either delete references aswell or add an on delete cascade.
    * */
    @Override
    public boolean deleteCustomerbyId(int id) {
        try {
            PreparedStatement prepDel = con.prepareStatement("DELETE FROM Klant WHERE Klantnummer = ?");

            prepDel.setInt(1, id);

            prepDel.executeUpdate();
            System.out.println("User with ID " + id + "succesfully deleted");

            return true;
        }
        catch (SQLException e){
            System.out.println("this error comes from method 3 in customerdao");
            System.out.println(e);
            e.printStackTrace();
            return false;
        }

    }


    /*
    *
    * This function updates the customers emailadress based on its customernumber
    * F.E customer 123 has a new emailadress they went from manager@org.com to ceo@org.com
    * Then you provide both 123 and the new emailadress in the function call and it will be updated in the database
    *
    * TODO:
    *
    *
    * test given function
    *
    * */

    @Override
    public void updateEmailbyID(int id, String email) {
        try{
            PreparedStatement prepUpdateEm = con.prepareStatement("UPDATE Klant" +
                    "SET Emailadress = ?" +
                    "WHERE Klantnummer = ?");

            prepUpdateEm.setString(1,email);
            prepUpdateEm.setInt(2,id);

            prepUpdateEm.executeQuery();

        }
        catch(SQLException e){
            System.out.println("this error comes from method 4 in customerdao");
            System.out.println(e);
            e.printStackTrace();
        }

    }

    @Override
    public boolean addCustomer(Customer customer) {
        try{
            PreparedStatement prepAddCus = con.prepareStatement("INSERT INTO Klant(Klantnummer,Bedrijfsnaam,KvKnummer,Emailadress,Telefoonnummer) VALUES(?,?,?,?,?)");

            prepAddCus.setInt(1,customer.getCustomerID());
            prepAddCus.setString(2,customer.getCompanyname());
            prepAddCus.setInt(3,customer.getKvkNumber());
            prepAddCus.setString(4,customer.getEmailadress());
            prepAddCus.setInt(5,customer.getPhonenumber());

            prepAddCus.executeUpdate();

            return true;

        }
        catch(SQLException e){
            System.out.println("this error comes from method 5 in customerdao");
            System.out.println(e);
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateCustomerInfo(String name, String email, int phone, int id) {
        try {
            PreparedStatement prepUpdateCus = con.prepareStatement("UPDATE Klant SET bedrijfsnaam = ?, emailadress = ?, telefoonnummer = ? WHERE klantnummer = ?");

            prepUpdateCus.setString(1,name);
            prepUpdateCus.setString(2,email);
            prepUpdateCus.setInt(3,phone);
            prepUpdateCus.setInt(4,id);

            prepUpdateCus.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("this error comes from method 5 in customerdao");
            System.out.println(e);
            e.printStackTrace();
            return false;
        }
    }


}


