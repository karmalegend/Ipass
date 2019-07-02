package nl.hu.ipass.project.webservices;

import nl.hu.ipass.project.persistance.OrderDaoPostgressImpl;
import nl.hu.ipass.project.persistance.ServiceDaoPostgressImpl;
import nl.hu.ipass.project.domein.Service;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;


@Path("/service")
public class serviceResource {

    @RolesAllowed("admin")
    @POST
    @Produces("application/json")
    public Response addServicetoCustomer(@FormParam("serviceID") int serviceID,
                                         @FormParam("packageID") int packageID,
                                         @FormParam("orderID") int orderID){
        OrderDaoPostgressImpl ordDao = new OrderDaoPostgressImpl();

        System.out.println("serviceID  " + serviceID + " package id " + packageID + " orderid " + orderID);

        if (!ordDao.addServiceToOrder(orderID,serviceID,packageID)){
            return Response.status(404).build();
        }

        return Response.ok().build();
    }

    @RolesAllowed("admin")
    @GET
    @Produces("application/json")
    @Path("/get/{serviceID}")
    public Service getServicebyId(@PathParam("serviceID") int id){
        System.out.println(id);
        ServiceDaoPostgressImpl serdao = new ServiceDaoPostgressImpl();
        Service ser = serdao.getServiceByID(id);
        System.out.println(ser);
        return(ser);
    }

    @RolesAllowed("admin")
    @GET
    @Produces("application/json")
    public ArrayList<Service> getAllServices(){
        ServiceDaoPostgressImpl serdao = new ServiceDaoPostgressImpl();

        return serdao.getAllServices();
    }

}
