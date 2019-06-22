package nl.hu.ipass.project.webservices;


import nl.hu.ipass.project.persistance.CustomerDaoPosgressImpl;
import nl.hu.ipass.project.persistance.pojos.Customer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.ArrayList;

@Path("/customer")
public class CustomerResource {

    @GET
    @Path("/all")
    @Produces("application/json")
    public ArrayList<Customer> allCust(){
        CustomerDaoPosgressImpl custDao = new CustomerDaoPosgressImpl();
        ArrayList<Customer> arlist = custDao.getAllCustomers();

        return arlist;

    }

}
