package nl.hu.ipass.project.persistance;


import nl.hu.ipass.project.persistance.DaoInterfaces.PackageDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PackageDaoPosgressImpl extends PostgresBaseDao implements PackageDao {
    private Connection con = getConnection();

    @Override
    public Package getPackagebyID(int id) {
        try{
            PreparedStatement getPackID = con.prepareStatement("SELECT * FROM Pakket" +
                    "WHERE PakketID = ?");

            getPackID.setInt(1,id);\

            ResultSet rs = getPackID.executeQuery();

            if(rs.next()) {
              int id =  rs.getInt("PakketID");
              String pakketnaam = rs.getString("Pakketnaam");
              int Pakketprijs =  rs.getInt("Pakketprijs");


                Package pack = new Package(id,pakketnaam,Pakketprijs);
            }

        }
        catch(SQLException e){
            System.out.println(e);
            e.printStackTrace();
        }
    }

    @Override
    public void deletePackagebyID(int id) {
        try{}
        catch(SQLException e){
            System.out.println(e);
            e.printStackTrace();
        }

    }

    @Override
    public void removeOrderbyId(int id) {
        try{}
        catch(SQLException e){
            System.out.println(e);
            e.printStackTrace();
        }

    }

    @Override
    public void addOrderByID(int id) {
        try{}
        catch(SQLException e){
            System.out.println(e);
            e.printStackTrace();
        }

    }

    @Override
    public Package getPackageByCustomerID(int id) {
        try{}
        catch(SQLException e){
            System.out.println(e);
            e.printStackTrace();
        }
        return null;
    }
}
