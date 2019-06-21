package nl.hu.ipass.project.persistance;

import nl.hu.ipass.project.persistance.DaoInterfaces.ServiceDao;
import nl.hu.ipass.project.persistance.pojos.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceDaoPostgressImpl extends PostgresBaseDao implements ServiceDao {
    private Connection con = getConnection();

    @Override
    public void addService(Service service) {
        try{
            PreparedStatement prepAddSer = con.prepareStatement("INSERT INTO Service(ServiceID,Servicenaam,Servicedienst,ServiceFrequentie)" +
                    "VALUES(?,?,?,?)");


            prepAddSer.setInt(1,service.getServiceID());
            prepAddSer.setString(2,service.getServiceName());
            prepAddSer.setString(3,service.getServiceDienst());
            prepAddSer.setString(4,service.getServiceFrequency());



            prepAddSer.executeQuery();

        }
        catch(SQLException e){
            System.out.println(e);
            e.printStackTrace();
        }

    }

    @Override
    public Service getServiceByID(int serid) {
        try{
            PreparedStatement prepGetSer = con.prepareStatement("SELECT * FROM Service" +
                    "WHERE ServiceID = ?");

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
        System.out.println(e);
        e.printStackTrace();
        }
        return null;

    }

    @Override
    public void deleteServiceByID(int id) {
        try {
            PreparedStatement delSer = con.prepareStatement("DELETE FROM Service WHERE ServiceID = ?");

            delSer.setInt(1, id);

            delSer.executeQuery();
        }
        catch(SQLException e){
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
