package nl.hu.ipass.project.persistance;

import nl.hu.ipass.project.persistance.DaoInterfaces.ServiceDao;
import nl.hu.ipass.project.domein.Service;

import java.sql.*;
import java.util.ArrayList;

public class ServiceDaoPostgressImpl extends PostgresBaseDao implements ServiceDao {


    /*Add a service to an existing order*/
    @Override
    public void addService(Service service) {
        try(Connection con = getConnection()){
            PreparedStatement prepAddSer = con.prepareStatement("INSERT INTO Service(ServiceID,Servicenaam,Servicedienst,ServiceFrequentie)" +
                    "VALUES(?,?,?,?)");

            prepAddSer.setInt(1,service.getServiceID());
            prepAddSer.setString(2,service.getServiceName());
            prepAddSer.setString(3,service.getServiceDienst());
            prepAddSer.setString(4,service.getServiceFrequency());



            prepAddSer.executeQuery();

        }
        catch(SQLException e){
            System.out.println("this error comes from method 1 in servicedao");
            System.out.println(e);
            e.printStackTrace();
        }

    }


    /*
    *
    * Get a service based off of it's ID
    * */
    @Override
    public Service getServiceByID(int serid) {
        try(Connection con = getConnection()){
            PreparedStatement prepGetSer = con.prepareStatement("SELECT * FROM Service WHERE ServiceID = ?");
            prepGetSer.setInt(1,serid);

            ResultSet service = prepGetSer.executeQuery();

            if (service.next()){
                int id = service.getInt("ServiceID");
                String servicename = service.getString("Servicenaam");
                String servcicedienst = service.getString("servicedienst");
                String freq = service.getString("ServiceFrequentie");

                Service ser = new Service(id,servicename,servcicedienst,freq);
                return ser;
            }

        }
      catch(SQLException e){
            System.out.println("this error comes from method 2 in servicedao");
        System.out.println(e);
        e.printStackTrace();
        }
        return null;

    }



    /*
    *
    * Delete a service by ID pretty sure this is a redundant
    * method
    *
    * */

    @Override
    public void deleteServiceByID(int id) {
        try(Connection con = getConnection()) {
            PreparedStatement delSer = con.prepareStatement("DELETE FROM Service WHERE ServiceID = ?");
            delSer.setInt(1, id);

            delSer.executeQuery();
        }
        catch(SQLException e){
            System.out.println("this error comes from method 3 in servicedao");
            System.out.println(e);
            e.printStackTrace();
        }
    }


    /*
    *
    *
    * get all the services
    *
    * Used to help new employees that don't yet know the services.
    *
    * Can be accesed via the navbar on the landingpage
    *
    * */
    @Override
    public ArrayList<Service> getAllServices() {
        try(Connection con = getConnection()){
            Statement smt = con.createStatement();
            ResultSet service = smt.executeQuery("SELECT * FROM Service");

            ArrayList<Service> services = new ArrayList<>();

            while (service.next())//noinspection duplicate
                 {
                int id = service.getInt("ServiceID");
                String servicename = service.getString("Servicenaam");
                String servcicedienst = service.getString("servicedienst");
                String freq = service.getString("ServiceFrequentie");

                Service ser = new Service(id,servicename,servcicedienst,freq);
                services.add(ser);
            }
            return services;

        }
        catch(SQLException e){
            System.out.println("this error comes from method 4 in servicedao");
            System.out.println(e);
            e.printStackTrace();
        }
        return null;

    }

}
